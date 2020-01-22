package io.reflectoring.coderadar.vcs.adapter;

import io.reflectoring.coderadar.vcs.UnableToGetCommitContentException;
import io.reflectoring.coderadar.vcs.port.driven.GetRawCommitContentPort;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.ObjectId;
import org.gitective.core.BlobUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class GetRawCommitContentAdapter implements GetRawCommitContentPort {
  @Override
  public byte[] getCommitContent(String projectRoot, String filepath, String name)
      throws UnableToGetCommitContentException {
    try (Git git = Git.open(new File(projectRoot))) {
      ObjectId commitId = git.getRepository().resolve(name);
      return BlobUtils.getRawContent(git.getRepository(), commitId, filepath);
    } catch (IOException e) {
      throw new UnableToGetCommitContentException(e.getMessage());
    }
  }

  @Override
  public HashMap<String, byte[]> getCommitContentBulk(
      String projectRoot, List<String> filepaths, String name)
      throws UnableToGetCommitContentException {
    try (Git git = Git.open(new File(projectRoot))) {
      ObjectId commitId = git.getRepository().resolve(name);
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
  public HashMap<io.reflectoring.coderadar.projectadministration.domain.File, byte[]>
      getCommitContentBulkWithFiles(
          String projectRoot,
          List<io.reflectoring.coderadar.projectadministration.domain.File> files,
          String name)
          throws UnableToGetCommitContentException {
    try (Git git = Git.open(new File(projectRoot))) {
      ObjectId commitId = git.getRepository().resolve(name);
      HashMap<io.reflectoring.coderadar.projectadministration.domain.File, byte[]> bulkContent =
          new LinkedHashMap<>();
      for (io.reflectoring.coderadar.projectadministration.domain.File file : files) {
        bulkContent.put(
            file, BlobUtils.getRawContent(git.getRepository(), commitId, file.getPath()));
      }
      return bulkContent;
    } catch (IOException e) {
      throw new UnableToGetCommitContentException(e.getMessage());
    }
  }
}
