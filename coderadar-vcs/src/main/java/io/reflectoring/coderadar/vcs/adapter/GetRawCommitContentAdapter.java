package io.reflectoring.coderadar.vcs.adapter;

import io.reflectoring.coderadar.projectadministration.domain.File;
import io.reflectoring.coderadar.vcs.UnableToGetCommitContentException;
import io.reflectoring.coderadar.vcs.port.driven.GetRawCommitContentPort;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.*;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.gitective.core.BlobUtils;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
public class GetRawCommitContentAdapter implements GetRawCommitContentPort {

  @Override
  public byte[] getCommitContent(String projectRoot, String filepath, String commitHash) {
    if (filepath.isEmpty()) {
      return new byte[0];
    }
    try (Git git = Git.open(new java.io.File(projectRoot))) {
      ObjectId commitId = git.getRepository().resolve(commitHash);
      return BlobUtils.getRawContent(git.getRepository(), commitId, filepath);
    } catch (IOException e) {
      throw new UnableToGetCommitContentException(e.getMessage());
    }
  }

  @Override
  public List<Pair<String, String>> getRenamesBetweenCommits(
      String parentHash, String commitHash, String projectRoot) {

    RevCommit commit;
    RevCommit parent;

    List<Pair<String, String>> result = new ArrayList<>();
    try (Git git = Git.open(new java.io.File(projectRoot))) {
      try (RevWalk revWalk = new RevWalk(git.getRepository())) {
        commit = revWalk.parseCommit(git.getRepository().resolve(commitHash));
        parent = revWalk.parseCommit(git.getRepository().resolve(parentHash));
      }

      RenameDetector renameDetector = new RenameDetector(git.getRepository());

      try (TreeWalk treeWalk = new TreeWalk(git.getRepository())) {
        treeWalk.setFilter(TreeFilter.ANY_DIFF);
        treeWalk.setRecursive(true);
        treeWalk.reset(parent.getTree(), commit.getTree());
        renameDetector.reset();
        renameDetector.addAll(DiffEntry.scan(treeWalk));
        for (DiffEntry ent : renameDetector.compute()) {
          if (ent.getChangeType() == DiffEntry.ChangeType.RENAME)
            result.add(Pair.of(ent.getOldPath(), ent.getNewPath()));
        }
      }
    } catch (IOException e) {
      throw new UnableToGetCommitContentException(e.getMessage());
    }
    return result;
  }

  @Override
  public byte[] getFileDiff(String projectRoot, String filepath, String commitHash) {
    if (filepath.isEmpty()) {
      return new byte[0];
    }
    try (Git git = Git.open(new java.io.File(projectRoot))) {
      RevCommit commit;
      try (RevWalk revWalk = new RevWalk(git.getRepository())) {
        commit = revWalk.parseCommit(git.getRepository().resolve(commitHash));
      }
      byte[] fpRawContent;
      if (commit.getParentCount() > 0) {
        fpRawContent = BlobUtils.getRawContent(git.getRepository(), commit.getParent(0), filepath);
      } else {
        fpRawContent = new byte[] {'\n'};
      }

      RawText rt1;
      rt1 = new RawText(Objects.requireNonNullElseGet(fpRawContent, () -> new byte[0]));
      byte[] rawContent = BlobUtils.getRawContent(git.getRepository(), commit, filepath);
      RawText rt2;
      if (rawContent != null && rawContent.length != 0) {
        rt2 = new RawText(BlobUtils.getRawContent(git.getRepository(), commit, filepath));
      } else {
        rt2 = new RawText(new byte[] {'\n'});
      }
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      new DiffFormatter(out)
          .format(new HistogramDiff().diff(RawTextComparator.DEFAULT, rt1, rt2), rt1, rt2);
      return out.toByteArray();
    } catch (IOException e) {
      throw new UnableToGetCommitContentException(e.getMessage());
    }
  }

  @Override
  public HashMap<String, byte[]> getCommitContentBulk(
      String projectRoot, List<String> filepaths, String commitHash) {
    try (Git git = Git.open(new java.io.File(projectRoot))) {
      ObjectId commitId = git.getRepository().resolve(commitHash);
      HashMap<String, byte[]> bulkContent = new LinkedHashMap<>();
      for (String filepath : filepaths) {
        bulkContent.put(filepath, BlobUtils.getRawContent(git.getRepository(), commitId, filepath));
      }
      return bulkContent;
    } catch (IOException e) {
      throw new UnableToGetCommitContentException(e.getMessage());
    }
  }

  @Override
  public HashMap<File, byte[]> getCommitContentBulkWithFiles(
      String projectRoot,
      List<io.reflectoring.coderadar.projectadministration.domain.File> files,
      String commitHash) {
    try (Git git = Git.open(new java.io.File(projectRoot))) {
      ObjectId commitId = git.getRepository().resolve(commitHash);
      HashMap<File, byte[]> bulkContent = new LinkedHashMap<>();
      for (File file : files) {
        bulkContent.put(
            file, BlobUtils.getRawContent(git.getRepository(), commitId, file.getPath()));
      }
      return bulkContent;
    } catch (IOException e) {
      throw new UnableToGetCommitContentException(e.getMessage());
    }
  }
}
