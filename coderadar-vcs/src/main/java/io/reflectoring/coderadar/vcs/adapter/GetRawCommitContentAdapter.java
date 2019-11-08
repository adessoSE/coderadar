package io.reflectoring.coderadar.vcs.adapter;

import io.reflectoring.coderadar.vcs.UnableToGetCommitContentException;
import io.reflectoring.coderadar.vcs.port.driven.GetRawCommitContentPort;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.ObjectId;
import org.gitective.core.BlobUtils;
import org.springframework.stereotype.Service;

@Service
public class GetRawCommitContentAdapter implements GetRawCommitContentPort {
  @Override
  public byte[] getCommitContent(String projectRoot, String filepath, String name)
      throws UnableToGetCommitContentException {
    try {
      Git git = Git.open(new File(projectRoot));
      ObjectId commitId = git.getRepository().resolve(name);
      byte[] content = BlobUtils.getRawContent(git.getRepository(), commitId, filepath);
      git.close();
      return content;
    } catch (IOException e) {
      throw new UnableToGetCommitContentException(e.getMessage());
    }
  }

  @Override
  public HashMap<String, byte[]> getCommitContentBulk(String projectRoot, List<String> filepaths, String name) throws UnableToGetCommitContentException {
    try {
      Git git = Git.open(new File(projectRoot));
      ObjectId commitId = git.getRepository().resolve(name);
      HashMap<String, byte[]> bulkContent = new LinkedHashMap<>();
      for (String filepath : filepaths) {
        bulkContent.put(filepath, BlobUtils.getRawContent(git.getRepository(), commitId, filepath));
      }
      git.close();
      return bulkContent;
    } catch (IOException e) {
      throw new UnableToGetCommitContentException(e.getMessage());
    }
  }
}
