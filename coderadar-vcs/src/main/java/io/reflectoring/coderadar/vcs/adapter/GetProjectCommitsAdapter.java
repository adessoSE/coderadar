package io.reflectoring.coderadar.vcs.adapter;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.analyzer.domain.FileToCommitRelationship;
import io.reflectoring.coderadar.plugin.api.ChangeType;
import io.reflectoring.coderadar.query.domain.DateRange;
import io.reflectoring.coderadar.vcs.ChangeTypeMapper;
import io.reflectoring.coderadar.vcs.port.driven.GetProjectCommitsPort;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.io.DisabledOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetProjectCommitsAdapter implements GetProjectCommitsPort {

  private final CoderadarConfigurationProperties coderadarConfigurationProperties;

  @Autowired
  public GetProjectCommitsAdapter(
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
                    commit.setParents(getParents(revWalk, rc, map, range.getStartDate()));
                  } catch (IOException e) {
                    e.printStackTrace();
                  }
                  map.put(rc, commit);
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

  /**
   * @param git The git API object .
   * @param firstCommit The firstCommit of the repository.
   * @param files A HashMap containing files already created for the project.
   * @throws IOException Thrown if the commit tree cannot be walked.
   */
  private void setFirstCommitFiles(
      Git git,
      Commit firstCommit,
      HashMap<String, io.reflectoring.coderadar.analyzer.domain.File> files)
      throws IOException {
    RevCommit gitCommit = findCommit(git, firstCommit.getName());
    try (TreeWalk treeWalk = new TreeWalk(git.getRepository())) {
      assert gitCommit != null;
      treeWalk.addTree(gitCommit.getTree());
      treeWalk.setRecursive(true);
      while (treeWalk.next()) {

        io.reflectoring.coderadar.analyzer.domain.File file = files.get(treeWalk.getPathString());

        if (file == null) {
          file = new io.reflectoring.coderadar.analyzer.domain.File();
        }
        FileToCommitRelationship fileToCommitRelationship = new FileToCommitRelationship();

        fileToCommitRelationship.setOldPath("/dev/null");
        fileToCommitRelationship.setChangeType(ChangeType.ADD);
        fileToCommitRelationship.setCommit(firstCommit);
        fileToCommitRelationship.setFile(file);
        file.setPath(treeWalk.getPathString());

        file.getCommits().add(fileToCommitRelationship);
        firstCommit.getTouchedFiles().add(fileToCommitRelationship);
        files.put(file.getPath(), file);
      }
    }
  }

  /**
   * @param git The git API object .
   * @param commits A list of commits.
   * @throws IOException Thrown if a commit cannot be processed.
   */
  private void setCommitsFiles(Git git, List<Commit> commits) throws IOException {
    commits.sort((o1, o2) -> o2.getTimestamp().compareTo(o1.getTimestamp()));
    HashMap<String, io.reflectoring.coderadar.analyzer.domain.File> files = new HashMap<>();
    DiffFormatter diffFormatter = new DiffFormatter(DisabledOutputStream.INSTANCE);
    diffFormatter.setRepository(git.getRepository());
    diffFormatter.setDiffComparator(RawTextComparator.DEFAULT);
    diffFormatter.setDetectRenames(true);
    for (Commit commit : commits) {
      RevCommit gitCommit = findCommit(git, commit.getName());

      assert gitCommit != null;
      if (gitCommit.getParentCount() > 0) {
        RevCommit parent = gitCommit.getParent(0);
        List<DiffEntry> diffs = diffFormatter.scan(parent, gitCommit);
        for (DiffEntry diff : diffs) {
          ChangeType changeType = ChangeTypeMapper.jgitToCoderadar(diff.getChangeType());
          if (changeType != ChangeType.UNCHANGED) {
            io.reflectoring.coderadar.analyzer.domain.File file = files.get(diff.getNewPath());
            if (file == null) {
              file = new io.reflectoring.coderadar.analyzer.domain.File();
            }
            FileToCommitRelationship fileToCommitRelationship = new FileToCommitRelationship();

            fileToCommitRelationship.setOldPath(diff.getOldPath());
            fileToCommitRelationship.setChangeType(changeType);
            fileToCommitRelationship.setCommit(commit);
            fileToCommitRelationship.setFile(file);

            if (changeType == ChangeType.DELETE) {
              file.setPath(diff.getOldPath());
            } else {
              file.setPath(diff.getNewPath());
            }

            file.getCommits().add(fileToCommitRelationship);
            commit.getTouchedFiles().add(fileToCommitRelationship);
            files.put(file.getPath(), file);
          }
        }
      }
    }
    setFirstCommitFiles(git, commits.get(commits.size() - 1), files);
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
   * Recursively walks through all the parents of a commit and builds the commit tree.
   *
   * @param revWalk RevWalk Object we use to efficiently get the parent commits of a any commit.
   * @param commit The start commit. This should be the newest commit.
   * @param walkedCommits A Map storing the commits we have walked so far, this prevents us from
   *     walking the same commits more than once.
   * @param startDate The date past which no parents are checked.
   * @return A List of parents for the commit.
   * @throws IOException Thrown if for any reason a parent commit cannot be properly parsed.
   */
  private List<Commit> getParents(
      RevWalk revWalk,
      RevCommit commit,
      HashMap<ObjectId, Commit> walkedCommits,
      LocalDate startDate)
      throws IOException {

    List<Commit> parents = new ArrayList<>();

    for (RevCommit rc : commit.getParents()) {

      Commit commitWithParents = walkedCommits.get(rc.getId());
      if (commitWithParents != null) {
        parents.add(commitWithParents);
      } else {
        RevCommit commitWithMetadata = revWalk.parseCommit(rc.getId());
        if (startDate == null
            || !Instant.ofEpochSecond(rc.getCommitTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .isAfter(startDate)) {
          continue;
        }
        commitWithParents = new Commit();
        commitWithParents.setName(commitWithMetadata.getName());
        commitWithParents.setAuthor(commitWithMetadata.getAuthorIdent().getName());
        commitWithParents.setComment(commitWithMetadata.getShortMessage());
        commitWithParents.setTimestamp(Date.from(Instant.ofEpochSecond(rc.getCommitTime())));
        walkedCommits.put(rc.getId(), commitWithParents);
        commitWithParents.setParents(
            getParents(revWalk, commitWithMetadata, walkedCommits, startDate));
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
    LocalDate commitTime =
        Instant.ofEpochSecond(rc.getCommitTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    return (commitTime.isBefore(range.getEndDate()) || commitTime.isEqual(range.getEndDate()))
        && (commitTime.isAfter(range.getStartDate()) || commitTime.isEqual(range.getStartDate()));
  }
}
