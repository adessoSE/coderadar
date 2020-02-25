package io.reflectoring.coderadar.vcs.adapter;

import com.google.common.collect.Iterables;
import io.reflectoring.coderadar.plugin.api.ChangeType;
import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.projectadministration.domain.File;
import io.reflectoring.coderadar.projectadministration.domain.FileToCommitRelationship;
import io.reflectoring.coderadar.query.domain.DateRange;
import io.reflectoring.coderadar.vcs.ChangeTypeMapper;
import io.reflectoring.coderadar.vcs.port.driven.ExtractProjectCommitsPort;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
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
  public List<Commit> extractCommits(java.io.File repositoryRoot, DateRange range) {
    try (Git git = Git.open(repositoryRoot)) {
      List<Commit> result = getCommits(git, range);
      setCommitsFiles(git, result);
      return result;
    } catch (Exception e) {
      throw new IllegalStateException(
          String.format("Error accessing git repository at %s", repositoryRoot), e);
    }
  }

  private List<Commit> getCommits(Git git, DateRange range) throws GitAPIException, IOException {
    List<RevCommit> revCommits = getAllRevCommits(git);

    int revCommitsSize = revCommits.size();
    HashMap<String, Commit> map = new HashMap<>(revCommitsSize);
    for (RevCommit rc : revCommits) {
      if (isInDateRange(range, rc)) {
        Commit commit = map.get(rc.getName());
        if (commit == null) {
          commit = mapRevCommitToCommit(rc);
        }
        List<Commit> parents =
            rc.getParentCount() > 0
                ? new ArrayList<>(rc.getParentCount())
                : Collections.emptyList();
        for (RevCommit parent : rc.getParents()) {
          if (isInDateRange(range, parent)) {
            Commit parentCommit = map.get(parent.getId().getName());
            if (parentCommit == null) {
              parentCommit = mapRevCommitToCommit(parent);
              map.put(parent.getName(), parentCommit);
            }
            parents.add(parentCommit);
          }
        }
        commit.setParents(parents);
        map.put(rc.getName(), commit);
      }
    }
    return new ArrayList<>(map.values());
  }

  private List<RevCommit> getAllRevCommits(Git git) throws GitAPIException, IOException {
    List<RevCommit> revCommits = new ArrayList<>();
    RevWalk revWalk = new RevWalk(git.getRepository());
    for (Ref ref : git.branchList().setListMode(ListBranchCommand.ListMode.ALL).call()) {
      revWalk.markStart(revWalk.parseCommit(ref.getObjectId()));
      revWalk
          .iterator()
          .forEachRemaining(
              revCommit -> {
                if (revCommits.stream()
                    .noneMatch(revCommit1 -> revCommit1.getId().equals(revCommit.getId()))) {
                  revCommits.add(revCommit);
                }
              });
      revWalk.reset();
    }
    revCommits.sort(Comparator.comparingLong(RevCommit::getCommitTime));
    return revCommits;
  }

  private Commit mapRevCommitToCommit(RevCommit rc) {
    Commit commit = new Commit();
    commit.setName(rc.getName());
    commit.setAuthor(rc.getAuthorIdent().getName());
    commit.setComment(rc.getShortMessage());
    commit.setTimestamp(rc.getAuthorIdent().getWhen().getTime());
    return commit;
  }

  /**
   * @param git The git API object.
   * @param firstCommit The firstCommit of the repository.
   * @param files A HashMap containing files already created for the project.
   * @param seqeunceId One element array with the current file sequence number.
   * @throws IOException Thrown if the commit tree cannot be walked.
   */
  private void setFirstCommitFiles(
      Git git, Commit firstCommit, HashMap<String, List<File>> files, long[] seqeunceId)
      throws IOException {
    RevCommit gitCommit = findCommit(git, firstCommit.getName());
    try (TreeWalk treeWalk = new TreeWalk(git.getRepository())) {
      assert gitCommit != null;
      treeWalk.setRecursive(true);
      treeWalk.addTree(gitCommit.getTree());
      while (treeWalk.next()) {
        File file = new File();
        file.setSequenceId(seqeunceId[0]++);
        file.setPath(treeWalk.getPathString());

        FileToCommitRelationship fileToCommitRelationship = new FileToCommitRelationship();
        fileToCommitRelationship.setOldPath("/dev/null");
        fileToCommitRelationship.setChangeType(ChangeType.ADD);
        fileToCommitRelationship.setFile(file);

        firstCommit.getTouchedFiles().add(fileToCommitRelationship);
        List<File> fileList = new ArrayList<>(1);
        fileList.add(file);
        files.put(file.getPath(), fileList);
      }
    }
  }

  /**
   * @param git The git API object .
   * @param commits A list of commits.
   * @throws IOException Thrown if a commit cannot be processed.
   */
  private void setCommitsFiles(Git git, List<Commit> commits) throws IOException {
    commits.sort(Comparator.comparingLong(Commit::getTimestamp));
    int commitsSize = commits.size();
    HashMap<String, List<File>> files = new HashMap<>((int) (commitsSize / 0.75) + 1);
    commits.get(0).setTouchedFiles(new ArrayList<>(commitsSize));
    long[] seqeunceId = new long[1];
    setFirstCommitFiles(git, commits.get(0), files, seqeunceId);
    DiffFormatter diffFormatter = new DiffFormatter(DisabledOutputStream.INSTANCE);
    diffFormatter.setRepository(git.getRepository());
    diffFormatter.setDiffComparator(RawTextComparator.DEFAULT);
    diffFormatter.setDetectRenames(true);
    for (int i = 1; i < commitsSize; i++) {
      RevCommit gitCommit = findCommit(git, commits.get(i).getName());
      if (gitCommit != null && gitCommit.getParentCount() > 0) {
        List<DiffEntry> diffs =
            new ArrayList<>(diffFormatter.scan(gitCommit.getParent(0), gitCommit));
        commits.get(i).setTouchedFiles(new ArrayList<>(diffs.size()));
        for (DiffEntry diff : diffs) {
          processDiffEntry(diff, files, commits.get(i), seqeunceId);
        }
      } else {
        commits.get(i).setTouchedFiles(Collections.emptyList());
      }
    }
  }

  /**
   * Processes a single diff entry. Sets the correct file to commit relationships for each commit
   *
   * @param diff The diff entry to process.
   * @param files All of the files walked so far.
   * @param seqeunceId One element array with the current file sequence number.
   * @param commit The current commit.
   */
  private void processDiffEntry(
      DiffEntry diff, HashMap<String, List<File>> files, Commit commit, long[] seqeunceId) {
    ChangeType changeType = ChangeTypeMapper.jgitToCoderadar(diff.getChangeType());
    if (changeType == ChangeType.UNCHANGED) {
      return;
    }
    List<File> filesWithPath = computeFilesToSave(diff, files, seqeunceId);
    for (File file : filesWithPath) {
      FileToCommitRelationship fileToCommitRelationship = new FileToCommitRelationship();
      fileToCommitRelationship.setOldPath(diff.getOldPath());
      fileToCommitRelationship.setChangeType(changeType);
      fileToCommitRelationship.setFile(file);
      commit.getTouchedFiles().add(fileToCommitRelationship);
    }
  }

  /**
   * Looks at already walked files and the change type of the diff entry to compute a list of files
   * to need to be created/updated and to set proper relationships between them.
   *
   * @param diff The current diff entry.
   * @param files The list of walked files.
   * @param seqeunceId One element array with the current file sequence number.
   * @return List of files to save.
   */
  private List<File> computeFilesToSave(
      DiffEntry diff, HashMap<String, List<File>> files, long[] seqeunceId) {
    String path = getFilepathFromDiffEntry(diff);
    List<File> existingFilesWithPath = files.get(path);
    List<File> filesToSave;
    File file = new File();
    file.setSequenceId(seqeunceId[0]++);
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
        filesToSave.add(Iterables.getLast(existingFilesWithPath));
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
   * @param gitClient The git API object.
   * @param commitName The name (hash) of the commit to look for.
   * @return A fully initialized RevCommit object
   */
  private RevCommit findCommit(Git gitClient, String commitName) {
    try {
      ObjectId commitId = gitClient.getRepository().resolve(commitName);
      Iterable<RevCommit> commits = gitClient.log().add(commitId).call();
      return commits.iterator().next();
    } catch (MissingObjectException e) {
      return null;
    } catch (Exception e) {
      throw new IllegalStateException(
          String.format(
              "Error accessing git repository at %s",
              gitClient.getRepository().getDirectory().getAbsolutePath()),
          e);
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
    return (commitTime.isBefore(range.getEndDate()) || commitTime.isEqual(range.getEndDate()))
        && (commitTime.isAfter(range.getStartDate()) || commitTime.isEqual(range.getStartDate()));
  }
}
