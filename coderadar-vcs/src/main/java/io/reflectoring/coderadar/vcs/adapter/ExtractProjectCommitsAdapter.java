package io.reflectoring.coderadar.vcs.adapter;

import io.reflectoring.coderadar.CoderadarConstants;
import io.reflectoring.coderadar.plugin.api.ChangeType;
import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.projectadministration.domain.File;
import io.reflectoring.coderadar.query.domain.DateRange;
import io.reflectoring.coderadar.vcs.ChangeTypeMapper;
import io.reflectoring.coderadar.vcs.RevCommitHelper;
import io.reflectoring.coderadar.vcs.port.driven.ExtractProjectCommitsPort;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.io.DisabledOutputStream;
import org.springframework.stereotype.Service;

@Service
public class ExtractProjectCommitsAdapter implements ExtractProjectCommitsPort {

  /**
   * @param repositoryRoot The root path of the local repository.
   * @param range The date range in which to collect commits
   * @return A list of fully initialized Commit objects (containing files and fileToCommit
   *     relationships).
   */
  public List<Commit> extractCommits(String repositoryRoot, DateRange range) throws IOException {
    try (Git git = Git.open(new java.io.File(repositoryRoot))) {
      List<RevCommit> revCommits = RevCommitHelper.getRevCommits(repositoryRoot);
      revCommits =
          revCommits.stream()
              .filter(revCommit -> isInDateRange(range, revCommit))
              .collect(Collectors.toList());
      List<Commit> commits = getCommits(revCommits);
      setCommitsFiles(git.getRepository(), commits, revCommits);
      return commits;
    }
  }

  private List<Commit> getCommits(List<RevCommit> revCommits) {
    int revCommitsSize = revCommits.size();
    IdentityHashMap<RevCommit, Commit> map = new IdentityHashMap<>(revCommitsSize);
    for (RevCommit rc : revCommits) {
      Commit commit = map.computeIfAbsent(rc, this::mapRevCommitToCommit);
      List<Commit> parents = new ArrayList<>(rc.getParentCount());
      for (RevCommit parent : rc.getParents()) {
        if (revCommits.contains(parent)) {
          Commit parentCommit = map.computeIfAbsent(parent, this::mapRevCommitToCommit);
          parents.add(parentCommit);
        }
      }
      commit.setParents(parents);
    }
    return new ArrayList<>(map.values());
  }

  private Commit mapRevCommitToCommit(RevCommit rc) {
    Commit commit = new Commit();
    commit.setHash(rc.abbreviate(CoderadarConstants.COMMIT_HASH_LENGTH).name());

    PersonIdent personIdent = rc.getAuthorIdent();
    commit.setAuthor(personIdent.getName());
    commit.setAuthorEmail(personIdent.getEmailAddress());
    commit.setTimestamp(personIdent.getWhen().getTime());

    // We trim the message to 200 chars
    String message = rc.getShortMessage();
    commit.setComment(message.substring(0, Math.min(message.length(), 200)));
    return commit;
  }

  /**
   * @param repository The git Repository object.
   * @param firstCommit The firstCommit of the repository.
   * @param files A HashMap containing files already created for the project.
   * @throws IOException Thrown if the commit tree cannot be walked.
   */
  private void setFirstCommitFiles(
      Repository repository,
      Commit firstCommit,
      RevCommit firstRevCommit,
      HashMap<String, List<File>> files)
      throws IOException {
    firstCommit.setChangedFiles(new ArrayList<>());
    try (TreeWalk treeWalk = new TreeWalk(repository)) {
      treeWalk.setRecursive(true);
      treeWalk.addTree(firstRevCommit.getTree());
      while (treeWalk.next()) {
        File file = new File();
        file.setPath(treeWalk.getPathString());
        firstCommit.getChangedFiles().add(file);
        List<File> fileList = new ArrayList<>(1);
        fileList.add(file);
        files.put(file.getPath(), fileList);
      }
    }
  }

  /**
   * @param repository The git Repository object .
   * @param commits A list of commits.
   * @throws IOException Thrown if a commit cannot be processed.
   */
  private void setCommitsFiles(
      Repository repository, List<Commit> commits, List<RevCommit> revCommits) throws IOException {
    commits.sort(Comparator.comparingLong(Commit::getTimestamp));
    int commitsSize = commits.size();
    HashMap<String, List<File>> files = new HashMap<>((int) (commitsSize / 0.75) + 1);
    setFirstCommitFiles(repository, commits.get(0), revCommits.get(0), files);
    DiffFormatter diffFormatter = new DiffFormatter(DisabledOutputStream.INSTANCE);
    diffFormatter.setRepository(repository);
    diffFormatter.setDiffComparator(RawTextComparator.DEFAULT);
    diffFormatter.setDetectRenames(true);

    for (int i = 1; i < commitsSize; ++i) {
      RevCommit gitCommit = revCommits.get(i);
      int parentCount = gitCommit.getParentCount();
      if (parentCount == 0) {
        continue;
      }

      List<DiffEntry> diffs = new ArrayList<>(diffFormatter.scan(gitCommit.getParent(0), gitCommit));

      for (int j = 1; j < parentCount; ++j) {
        List<DiffEntry> diffEntries = diffFormatter.scan(gitCommit.getParent(j), gitCommit);
        for (DiffEntry diff : diffEntries) {
          if (diff.getChangeType().equals(DiffEntry.ChangeType.DELETE)
              || diff.getChangeType().equals(DiffEntry.ChangeType.RENAME)) {
            diffs.add(diff);
          }
        }
      }

      for (DiffEntry diff : diffs) {
        processDiffEntry(diff, files, commits.get(i));
      }
    }
  }

  /**
   * Processes a single diff entry. Sets the correct file to commit relationships for each commit
   *
   * @param diff The diff entry to process.
   * @param files All of the files walked so far.
   * @param commit The current commit.
   */
  private void processDiffEntry(DiffEntry diff, HashMap<String, List<File>> files, Commit commit) {
    ChangeType changeType = ChangeTypeMapper.jgitToCoderadar(diff.getChangeType());
    if (changeType.equals(ChangeType.UNCHANGED)) {
      return;
    }
    List<File> filesWithPath = computeFilesToSave(diff, files);
    for (File file : filesWithPath) {
      if (!changeType.equals(ChangeType.DELETE)) {
        commit.getChangedFiles().add(file);
      } else {
        commit.getDeletedFiles().add(file);
      }
    }
  }

  /**
   * Looks at already walked files and the change type of the diff entry to compute a list of files
   * to need to be created/updated and to set proper relationships between them.
   *
   * @param diff The current diff entry.
   * @param files The list of walked files.
   * @return List of files to save.
   */
  private List<File> computeFilesToSave(DiffEntry diff, HashMap<String, List<File>> files) {
    String path = getFilepathFromDiffEntry(diff);
    List<File> existingFilesWithPath = files.get(path);
    List<File> filesToSave;
    File file = new File();
    file.setPath(path);
    if (existingFilesWithPath == null) {
      if ((diff.getChangeType().equals(DiffEntry.ChangeType.RENAME))) {
        List<File> filesWithOldPath = files.get(diff.getOldPath());
        if (filesWithOldPath != null) {
          file.setOldFiles(new ArrayList<>(filesWithOldPath));
        }
      }
      filesToSave = new ArrayList<>(1);
      filesToSave.add(file);
      files.put(file.getPath(), filesToSave);
    } else {
      if ((diff.getChangeType().equals(DiffEntry.ChangeType.ADD))) {
        filesToSave = new ArrayList<>(1);
        filesToSave.add(file);
        existingFilesWithPath.add(file);
        files.put(file.getPath(), existingFilesWithPath);
      } else if ((diff.getChangeType().equals(DiffEntry.ChangeType.DELETE))) {
        filesToSave = new ArrayList<>(existingFilesWithPath);
      } else if ((diff.getChangeType().equals(DiffEntry.ChangeType.RENAME))) {
        List<File> filesWithOldPath = files.get(diff.getOldPath());
        if (filesWithOldPath != null) {
          file.setOldFiles(new ArrayList<>(filesWithOldPath));
        }
        filesToSave = new ArrayList<>(1);
        filesToSave.add(file);
        existingFilesWithPath.add(file);
        files.put(file.getPath(), existingFilesWithPath);
      } else {
        filesToSave = new ArrayList<>(1);
        filesToSave.add(existingFilesWithPath.get(existingFilesWithPath.size() - 1));
      }
    }
    return filesToSave;
  }

  /**
   * @param diff The diff entry to check.
   * @return Correct path for the current diffEntry.
   */
  private String getFilepathFromDiffEntry(DiffEntry diff) {
    if (diff.getChangeType().equals(DiffEntry.ChangeType.DELETE)) {
      return diff.getOldPath();
    } else {
      return diff.getNewPath();
    }
  }

  /**
   * @param range Date range to test for
   * @param rc RevCommit to check
   * @return True if the commit was made within the date range, false otherwise.
   */
  private boolean isInDateRange(DateRange range, RevCommit rc) {
    LocalDate commitTime =
        Instant.ofEpochSecond(rc.getCommitTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    return range.containsDate(commitTime);
  }
}
