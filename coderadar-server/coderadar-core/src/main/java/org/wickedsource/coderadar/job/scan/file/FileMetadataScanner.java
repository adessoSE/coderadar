package org.wickedsource.coderadar.job.scan.file;

import static com.codahale.metrics.MetricRegistry.name;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import java.io.IOException;
import java.util.List;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.io.DisabledOutputStream;
import org.gitective.core.BlobUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.analyzer.api.ChangeType;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.file.domain.GitLogEntry;
import org.wickedsource.coderadar.file.domain.GitLogEntryRepository;
import org.wickedsource.coderadar.filepattern.domain.FilePattern;
import org.wickedsource.coderadar.filepattern.domain.FilePatternRepository;
import org.wickedsource.coderadar.filepattern.match.FilePatternMatcher;
import org.wickedsource.coderadar.job.LocalGitRepositoryManager;
import org.wickedsource.coderadar.job.core.FirstCommitFinder;
import org.wickedsource.coderadar.vcs.git.ChangeTypeMapper;
import org.wickedsource.coderadar.vcs.git.GitCommitFinder;

@Service
public class FileMetadataScanner {

  private Logger logger = LoggerFactory.getLogger(FileMetadataScanner.class);

  private LocalGitRepositoryManager gitRepoManager;

  private CommitRepository commitRepository;

  private GitCommitFinder commitFinder;

  private ChangeTypeMapper changeTypeMapper = new ChangeTypeMapper();

  private GitLogEntryRepository logEntryRepository;

  private Meter filesMeter;

  private Meter commitsMeter;

  private FirstCommitFinder firstCommitFinder;

  private FilePatternRepository filePatternRepository;

  private Hasher hasher = new Hasher();

  @Autowired
  public FileMetadataScanner(
      LocalGitRepositoryManager gitRepoManager,
      CommitRepository commitRepository,
      GitCommitFinder commitFinder,
      GitLogEntryRepository logEntryRepository,
      MetricRegistry metricRegistry,
      FirstCommitFinder firstCommitFinder,
      FilePatternRepository filePatternRepository) {
    this.gitRepoManager = gitRepoManager;
    this.commitRepository = commitRepository;
    this.commitFinder = commitFinder;
    this.logEntryRepository = logEntryRepository;
    this.filesMeter = metricRegistry.meter(name(FileMetadataScanner.class, "files"));
    this.commitsMeter = metricRegistry.meter(name(FileMetadataScanner.class, "commits"));
    this.firstCommitFinder = firstCommitFinder;
    this.filePatternRepository = filePatternRepository;
  }

  /**
   * Goes through all files that were touched in a commit and stores meta data about the files in a
   * "git log" like database table (see {@link GitLogEntry}).
   *
   * @param commit the commit whose files to extract and store in the database.
   */
  public void scan(Commit commit) {
    try {
      if (commit == null) {
        throw new IllegalArgumentException(String.format("argument 'commit' must not be null!"));
      }
      Git gitClient = gitRepoManager.getLocalGitRepository(commit.getProject().getId());
      FilePatternMatcher matcher = getPatternMatcher(commit.getProject().getId());
      if (firstCommitFinder.isFirstCommitInProject(commit)) {
        walkAllFilesInCommit(gitClient, commit, matcher);
      } else {
        walkTouchedFilesInCommit(gitClient, commit, matcher);
      }
      commitsMeter.mark();
    } catch (IOException e) {
      throw new IllegalStateException(
          String.format("error while scanning commit %d", commit.getId()), e);
    }
  }

  private void walkAllFilesInCommit(Git gitClient, Commit commit, FilePatternMatcher matcher)
      throws IOException {
    int fileCounter = 0;
    RevCommit gitCommit = commitFinder.findCommit(gitClient, commit.getName());
    try (TreeWalk treeWalk = new TreeWalk(gitClient.getRepository())) {
      treeWalk.addTree(gitCommit.getTree());
      treeWalk.setRecursive(true);
      while (treeWalk.next()) {

        if (!matcher.matches(treeWalk.getPathString())) {
          continue;
        }

        String hash = createHash(gitClient, gitCommit, treeWalk.getPathString());

        GitLogEntry logEntry = new GitLogEntry();
        logEntry.setOldFilepath("/dev/null");
        logEntry.setFilepath(treeWalk.getPathString());
        logEntry.setProject(commit.getProject());
        logEntry.setChangeType(ChangeType.ADD);
        logEntry.setCommit(commit);
        logEntry.setFileHash(hash);

        logEntryRepository.save(logEntry);
        fileCounter++;
        filesMeter.mark();
      }
    }
    commit.setScanned(true);
    commitRepository.save(commit);
    logger.info("scanned {} files in commit {}", fileCounter, commit);
  }

  private boolean entryWithSameFileAlreadyExists(
      long projectId, String filepath, ChangeType changeType, String fileHash) {
    return logEntryRepository.countByProjectIdAndFilepathAndChangeTypeAndFileHash(
            projectId, filepath, changeType, fileHash)
        > 0;
  }

  private String createHash(Git gitClient, RevCommit commit, String filepath) {
    byte[] file = BlobUtils.getRawContent(gitClient.getRepository(), commit.getId(), filepath);
    return hasher.hash(file);
  }

  private FilePatternMatcher getPatternMatcher(long projectId) {
    List<FilePattern> sourceFilePatterns = filePatternRepository.findByProjectId(projectId);
    return new FilePatternMatcher(sourceFilePatterns);
  }

  private void walkTouchedFilesInCommit(Git gitClient, Commit commit, FilePatternMatcher matcher)
      throws IOException {
    RevCommit gitCommit = commitFinder.findCommit(gitClient, commit.getName());
    DiffFormatter diffFormatter = new DiffFormatter(DisabledOutputStream.INSTANCE);
    diffFormatter.setRepository(gitClient.getRepository());
    diffFormatter.setDiffComparator(RawTextComparator.DEFAULT);
    diffFormatter.setDetectRenames(true);

    int fileCounter = 0;
    if (gitCommit.getParentCount() > 0) {
      RevCommit parent = gitCommit.getParent(0);
      List<DiffEntry> diffs = diffFormatter.scan(parent, gitCommit);
      for (DiffEntry diff : diffs) {

        if (!matcher.matches(diff.getNewPath())) {
          continue;
        }

        String hash = createHash(gitClient, gitCommit, diff.getNewPath());
        ChangeType changeType = changeTypeMapper.jgitToCoderadar(diff.getChangeType());
        if (changeType == ChangeType.ADD || changeType == ChangeType.COPY) {
          // we want to add ADDED and COPIED log entries only once for the same file
          if (entryWithSameFileAlreadyExists(
              commit.getProject().getId(), diff.getNewPath(), changeType, hash)) {
            continue;
          }
        }

        GitLogEntry logEntry = new GitLogEntry();
        logEntry.setOldFilepath(diff.getOldPath());
        logEntry.setFilepath(diff.getNewPath());
        logEntry.setProject(commit.getProject());
        logEntry.setChangeType(changeType);
        logEntry.setCommit(commit);
        logEntry.setFileHash(hash);

        logEntryRepository.save(logEntry);
        fileCounter++;
        filesMeter.mark();
      }
    }
    commit.setScanned(true);
    commitRepository.save(commit);
    logger.info("scanned {} files in commit {}", fileCounter, commit);
  }
}
