package io.reflectoring.coderadar.vcs.adapter;

import com.google.common.collect.Iterables;
import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.plugin.api.ChangeType;
import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.projectadministration.domain.File;
import io.reflectoring.coderadar.projectadministration.domain.FileToCommitRelationship;
import io.reflectoring.coderadar.query.domain.DateRange;
import io.reflectoring.coderadar.vcs.ChangeTypeMapper;
import io.reflectoring.coderadar.vcs.port.driven.ExtractProjectCommitsPort;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.io.DisabledOutputStream;
import org.springframework.stereotype.Service;

@Service
public class ExtractProjectCommitsAdapter implements ExtractProjectCommitsPort {

  private final CoderadarConfigurationProperties coderadarConfigurationProperties;

  public ExtractProjectCommitsAdapter(
      CoderadarConfigurationProperties coderadarConfigurationProperties) {
    this.coderadarConfigurationProperties = coderadarConfigurationProperties;
  }

  /**
   * @param repositoryRoot The root path of the local repository.
   * @param range The date range in which to collect commits
   * @return A list of fully initialized Commit objects (containing files and fileToCommit
   *     relationships).
   */
  public List<Commit> getCommits(Path repositoryRoot, DateRange range) {
    Git git;
    try {
      Path actualPath =
          Paths.get(coderadarConfigurationProperties.getWorkdir() + "/projects/" + repositoryRoot);

      FileRepositoryBuilder builder = new FileRepositoryBuilder();
      Repository repository =
          builder
              .setWorkTree(actualPath.toFile())
              .setGitDir(new java.io.File(actualPath + "/.git"))
              .build();
      git = new Git(repository);

      HashMap<ObjectId, Commit> map = new HashMap<>();
      List<RevCommit> revCommits = new ArrayList<>();
      List<Commit> result = new ArrayList<>();
      git.log().call().iterator().forEachRemaining(revCommits::add);
      Collections.reverse(revCommits);
      for (RevCommit rc : revCommits) {
        if (isInDateRange(range, rc)) {
          Commit commit = map.get(rc.getId());
          if (commit == null) {
            commit = mapRevCommitToCommit(rc);
          }
          for (RevCommit parent : rc.getParents()) {
            if (isInDateRange(range, parent)) {
              Commit parentCommit = map.get(parent.getId());
              if (parentCommit == null) {
                parentCommit = mapRevCommitToCommit(parent);
                map.put(parent.getId(), parentCommit);
                result.add(parentCommit);
              }
              commit.getParents().add(parentCommit);
            }
          }
          map.put(rc.getId(), commit);
          result.add(commit);
        }
      }

      setCommitsFiles(git, result);
      git.getRepository().close();
      git.close();
      return result;
    } catch (Exception e) {
      throw new IllegalStateException(
          String.format("Error accessing git repository at %s", repositoryRoot), e);
    }
  }

  private Commit mapRevCommitToCommit(RevCommit rc) {
    Commit commit = new Commit();
    commit.setName(rc.getName());
    commit.setAuthor(rc.getAuthorIdent().getName());
    commit.setComment(rc.getShortMessage());
    commit.setTimestamp(rc.getAuthorIdent().getWhen().toInstant().toEpochMilli());
    return commit;
  }

  /**
   * @param git The git API object.
   * @param firstCommit The firstCommit of the repository.
   * @param files A HashMap containing files already created for the project.
   * @throws IOException Thrown if the commit tree cannot be walked.
   */
  private void setFirstCommitFiles(Git git, Commit firstCommit, HashMap<String, List<File>> files)
      throws IOException {
    RevCommit gitCommit = findCommit(git, firstCommit.getName());
    try (TreeWalk treeWalk = new TreeWalk(git.getRepository())) {
      assert gitCommit != null;
      treeWalk.setRecursive(true);
      treeWalk.addTree(gitCommit.getTree());
      while (treeWalk.next()) {
        File file;

        file = new File();
        FileToCommitRelationship fileToCommitRelationship = new FileToCommitRelationship();

        fileToCommitRelationship.setOldPath("/dev/null");
        fileToCommitRelationship.setChangeType(ChangeType.ADD);
        fileToCommitRelationship.setCommit(firstCommit);
        fileToCommitRelationship.setFile(file);
        file.setPath(treeWalk.getPathString());

        file.getCommits().add(fileToCommitRelationship);
        firstCommit.getTouchedFiles().add(fileToCommitRelationship);
        List<File> fileList = new ArrayList<>();
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
    HashMap<String, List<File>> files = new HashMap<>();
    setFirstCommitFiles(git, commits.get(0), files);
    DiffFormatter diffFormatter = new DiffFormatter(DisabledOutputStream.INSTANCE);
    diffFormatter.setRepository(git.getRepository());
    diffFormatter.setDiffComparator(RawTextComparator.DEFAULT);
    diffFormatter.setDetectRenames(true);
    for (int i = 1; i < commits.size(); i++) {
      RevCommit gitCommit = findCommit(git, commits.get(i).getName());
      assert gitCommit != null;
      if (gitCommit.getParentCount() > 0) {
        RevCommit parent = gitCommit.getParent(0);
        List<DiffEntry> diffs = diffFormatter.scan(parent, gitCommit);
        for (DiffEntry diff : diffs) {
          processDiffEntry(diff, files, commits.get(i));
        }
      }
    }
  }

  /**
   * Processes a single diff entry. Sets the correct file to commit relationships for each commit
   *
   * @param diff The diff entry to process
   * @param files All of the files walked so far
   * @param commit The current commit
   */
  private void processDiffEntry(DiffEntry diff, HashMap<String, List<File>> files, Commit commit) {
    ChangeType changeType = ChangeTypeMapper.jgitToCoderadar(diff.getChangeType());
    if (changeType == ChangeType.UNCHANGED) {
      return;
    }
    List<File> filesWithPath = computeFilesToSave(diff, files);
    for (File file : filesWithPath) {
      FileToCommitRelationship fileToCommitRelationship = new FileToCommitRelationship();
      fileToCommitRelationship.setOldPath(diff.getOldPath());
      fileToCommitRelationship.setChangeType(changeType);
      fileToCommitRelationship.setCommit(commit);
      fileToCommitRelationship.setFile(file);
      file.getCommits().add(fileToCommitRelationship);
      commit.getTouchedFiles().add(fileToCommitRelationship);
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
    List<File> filesToSave = new ArrayList<>();
    File file = new File();
    file.setPath(path);
    if (existingFilesWithPath == null) {
      if ((diff.getChangeType().equals(DiffEntry.ChangeType.RENAME))) {
        List<File> filesWithOldPath = files.get(diff.getOldPath());
        if (filesWithOldPath != null) {
          file.getOldFiles().addAll(filesWithOldPath);
        }
      }
      filesToSave.add(file);
      files.put(file.getPath(), filesToSave);
    } else {
      if ((diff.getChangeType().equals(DiffEntry.ChangeType.ADD))) {
        filesToSave.add(file);
        existingFilesWithPath.add(file);
        files.put(file.getPath(), existingFilesWithPath);
      } else if ((diff.getChangeType().equals(DiffEntry.ChangeType.DELETE))) {
        filesToSave.addAll(existingFilesWithPath);
      } else if ((diff.getChangeType().equals(DiffEntry.ChangeType.RENAME))) {
        List<File> filesWithOldPath = files.get(diff.getOldPath());
        if (filesWithOldPath != null) {
          file.getOldFiles().addAll(filesWithOldPath);
        }
        filesToSave.add(file);
        existingFilesWithPath.add(file);
        files.put(file.getPath(), existingFilesWithPath);
      } else {
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
