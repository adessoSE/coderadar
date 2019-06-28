package io.reflectoring.coderadar.vcs.adapter;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.analyzer.domain.FileToCommitRelationship;
import io.reflectoring.coderadar.plugin.api.ChangeType;
import io.reflectoring.coderadar.query.domain.DateRange;
import io.reflectoring.coderadar.vcs.ChangeTypeMapper;
import io.reflectoring.coderadar.vcs.port.driven.GetProjectCommitsPort;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.util.io.DisabledOutputStream;
import org.gitective.core.BlobUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class GetProjectCommitsAdapter implements GetProjectCommitsPort {

  private final CoderadarConfigurationProperties coderadarConfigurationProperties;
  private Logger logger = LoggerFactory.getLogger(GetProjectCommitsAdapter.class);

  @Autowired
  public GetProjectCommitsAdapter(
      CoderadarConfigurationProperties coderadarConfigurationProperties) {
    this.coderadarConfigurationProperties = coderadarConfigurationProperties;
  }

  public List<Commit> getCommits(Path repositoryRoot, DateRange range) {
    Git git;
    try {
      Repository repository;
      Path actualPath =
          Paths.get(coderadarConfigurationProperties.getWorkdir() + "/projects/" + repositoryRoot);

      FileRepositoryBuilder builder = new FileRepositoryBuilder();
      repository =
          builder
              .setWorkTree(actualPath.toFile())
              .setGitDir(new File(actualPath + "/.git"))
              .build();
      git = new Git(repository);

      HashMap<ObjectId, Commit> map = new HashMap<>();
      AtomicReference<Boolean> done = new AtomicReference<>(false);
      git.log()
          .call()
          .forEach(
              rc -> {
                // Find the first commit in the given date range and build the tree from it.
                if (!done.get() && isInDateRange(range, rc)) {
                  final RevWalk revWalk = new RevWalk(git.getRepository());

                  Commit commit = new Commit();
                  commit.setName(rc.getName());
                  commit.setAuthor(rc.getAuthorIdent().getName());
                  commit.setComment(rc.getShortMessage());
                  commit.setTimestamp(Date.from(Instant.ofEpochSecond(rc.getCommitTime())));
                  try {
                    commit.setParents(getParents(revWalk, rc, map, range.getEndDate()));
                  } catch (IOException e) {
                    e.printStackTrace();
                  }
                  done.set(true);
                }
              });
      List<Commit> result = new ArrayList<>(map.values());
      setCommitsFiles(git, result);
      return result;
    } catch (Exception e) {
      throw new IllegalStateException(
          String.format("Error accessing git repository at %s", repositoryRoot), e);
    }
  }

  private void setCommitsFiles(Git git, List<Commit> result) throws IOException {
    result.sort((o1, o2) -> o2.getTimestamp().compareTo(o1.getTimestamp()));
    HashMap<String, io.reflectoring.coderadar.analyzer.domain.File> files = new HashMap<>();
    for(Commit commit : result){
      int fileCounter = 0;
      RevCommit gitCommit = findCommit(git, commit.getName());
      DiffFormatter diffFormatter = new DiffFormatter(DisabledOutputStream.INSTANCE);
      diffFormatter.setRepository(git.getRepository());
      diffFormatter.setDiffComparator(RawTextComparator.DEFAULT);
      diffFormatter.setDetectRenames(true);

      if (gitCommit.getParentCount() > 0) {
        RevCommit parent = gitCommit.getParent(0);
        List<DiffEntry> diffs = diffFormatter.scan(parent, gitCommit);
        for (DiffEntry diff : diffs) {
/*          if (!matcher.matches(diff.getNewPath())) {
            continue;
          }*/

          System.out.println(diff.getNewPath());
          ChangeType changeType = ChangeTypeMapper.jgitToCoderadar(diff.getChangeType());
          System.out.println(changeType);
          if (changeType != ChangeType.UNCHANGED && changeType != ChangeType.DELETE) {
            // we want to add ADDED and COPIED log entries only once for the same file
/*            if (entryWithSameFileAlreadyExists(
                    commit.getProject().getId(), diff.getNewPath(), changeType, hash)) {
              continue;
            }*/

            String hash = createHash(git, gitCommit, diff.getNewPath());
            io.reflectoring.coderadar.analyzer.domain.File file = files.get(hash);

            if (file == null) {
              file = new io.reflectoring.coderadar.analyzer.domain.File();
            }
            FileToCommitRelationship fileToCommitRelationship = new FileToCommitRelationship();

            fileToCommitRelationship.setOldPath(diff.getOldPath());
            fileToCommitRelationship.setChangeType(changeType);
            fileToCommitRelationship.setCommit(commit);
            fileToCommitRelationship.setFile(file);
            file.setPath(diff.getNewPath());

            file.getCommits().add(fileToCommitRelationship);
            commit.getTouchedFiles().add(fileToCommitRelationship);
            files.put(hash, file);
            fileCounter++;
          }
       }
      }
      logger.info("scanned {} files in commit {}", fileCounter, commit);
    }
  }

  private String createHash(Git gitClient, RevCommit commit, String filepath) {
    byte[] file = BlobUtils.getRawContent(gitClient.getRepository(), commit.getId(), filepath);
    return hash(file);
  }

  private String hash(byte[] content) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-1");
      return DatatypeConverter.printHexBinary(digest.digest(content));
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException(e);
    }
  }

/*  RevCommit gitCommit = findCommit(git, commit.getName());
      try (TreeWalk treeWalk = new TreeWalk(git.getRepository())) {
    treeWalk.addTree(gitCommit.getTree());
    treeWalk.setRecursive(true);
    while (treeWalk.next()) {
      FileToCommitRelationship fileToCommitRelationship = new FileToCommitRelationship();
      fileToCommitRelationship.setCommit(commit);
      fileToCommitRelationship.setChangeType(ChangeType.ADD);
      fileToCommitRelationship.setOldPath(treeWalk.get);
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
  }*/

  public RevCommit findCommit(Git gitClient, String commitName) {
    try {
      ObjectId commitId = gitClient.getRepository().resolve(commitName);
      Iterable<RevCommit> commits = gitClient.log().add(commitId).call();
      return commits.iterator().next();
    } catch (MissingObjectException e) {
      return null;
    } catch (Exception e) {
      throw new IllegalStateException(
              String.format(
                      "error accessing git repository at %s",
                      gitClient.getRepository().getDirectory().getAbsolutePath()),
              e);
    }
  }

  private AbstractTreeIterator prepareTreeParser(Repository repository, String ref) throws IOException {
    // from the commit we can build the tree which allows us to construct the TreeParser
    Ref head = repository.exactRef(ref);
    try (RevWalk walk = new RevWalk(repository)) {
      RevCommit commit = walk.parseCommit(head.getObjectId());
      RevTree tree = walk.parseTree(commit.getTree().getId());

      CanonicalTreeParser treeParser = new CanonicalTreeParser();
      try (ObjectReader reader = repository.newObjectReader()) {
        treeParser.reset(reader, tree.getId());
      }
      walk.dispose();
      return treeParser;
    }
  }

  /**
   * Recursively walks through all the parents of a commit and builds the commit tree.
   *
   * @param revWalk RevWalk Object we use to efficiently get the parent commits of a any commit.
   * @param commit The start commit. This should be the newest commit.
   * @param walkedCommits A Map storing the commits we have walked so far, this prevents us from
   *     walking the same commits more than once.
   * @param endDate The date past which no parents are checked.
   * @return A List of parents for the commit.
   * @throws IOException Thrown if for any reason a parent commit cannot be properly parsed.
   */
  private List<Commit> getParents(
      RevWalk revWalk, RevCommit commit, HashMap<ObjectId, Commit> walkedCommits, LocalDate endDate)
      throws IOException {

    List<Commit> parents = new ArrayList<>();

    for (RevCommit rc : commit.getParents()) {

      Commit commitWithParents = walkedCommits.get(rc.getId());
      if (commitWithParents != null) {
        parents.add(commitWithParents);
      } else {
        RevCommit commitWithMetadata = revWalk.parseCommit(rc.getId());
        if (endDate == null
            || !Instant.ofEpochSecond(rc.getCommitTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .isBefore(endDate)) {
          continue;
        }
        commitWithParents = new Commit();
        commitWithParents.setName(commitWithMetadata.getName());
        commitWithParents.setAuthor(commitWithMetadata.getAuthorIdent().getName());
        commitWithParents.setComment(commitWithMetadata.getShortMessage());
        commitWithParents.setTimestamp(Date.from(Instant.ofEpochSecond(rc.getCommitTime())));
        walkedCommits.put(rc.getId(), commitWithParents);
        commitWithParents.setParents(
            getParents(revWalk, commitWithMetadata, walkedCommits, endDate));
        parents.add(commitWithParents);
      }
    }
    return parents;
  }

  /**
   * @param range Date range to test for
   * @param rc RevCommit to check
   * @return True if the commit was made within the date range, false otherwise.
   */
  private boolean isInDateRange(DateRange range, RevCommit rc) {
    return Instant.ofEpochSecond(rc.getCommitTime())
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .isBefore(range.getEndDate())
        && Instant.ofEpochSecond(rc.getCommitTime())
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .isAfter(range.getStartDate());
  }
}
