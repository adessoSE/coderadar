package io.reflectoring.coderadar.vcs.adapter;

import io.reflectoring.coderadar.projectadministration.domain.File;
import io.reflectoring.coderadar.vcs.UnableToGetCommitContentException;
import io.reflectoring.coderadar.vcs.port.driven.GetRawCommitContentPort;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.HistogramDiff;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.gitective.core.BlobUtils;
import org.springframework.stereotype.Service;

@Service
public class GetRawCommitContentAdapter implements GetRawCommitContentPort {

  @Override
  public byte[] getCommitContent(String projectRoot, String filepath, String commitHash)
      throws UnableToGetCommitContentException {
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
  public byte[] getFileDiff(String projectRoot, String filepath, String commitHash)
      throws UnableToGetCommitContentException {
    if (filepath.isEmpty()) {
      return new byte[0];
    }
    try (Git git = Git.open(new java.io.File(projectRoot))) {
      ObjectId commitId = ObjectId.fromString(commitHash);
      RevCommit commit;
      try (RevWalk revWalk = new RevWalk(git.getRepository())) {
        commit = revWalk.parseCommit(commitId);
      }
      RevCommit firstParent = commit.getParent(0);
      byte[] fpRawContent = BlobUtils.getRawContent(git.getRepository(), firstParent, filepath);
      RawText rt1;
      if (fpRawContent != null) {
        rt1 = new RawText(fpRawContent);
      } else {
        rt1 = new RawText(new byte[0]);
      }
      RawText rt2 = new RawText(BlobUtils.getRawContent(git.getRepository(), commit, filepath));
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
      String projectRoot, List<String> filepaths, String commitHash)
      throws UnableToGetCommitContentException {
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
      String commitHash)
      throws UnableToGetCommitContentException {
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
