package io.reflectoring.coderadar.vcs.adapter;

import io.reflectoring.coderadar.vcs.UnableToGetCommitContentException;
import io.reflectoring.coderadar.vcs.port.driven.GetRawCommitContentPort;
import java.io.File;
import java.io.IOException;
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
      //git.getRepository().close();
      git.close();
      return content;
    } catch (IOException e) {
      throw new UnableToGetCommitContentException(e.getMessage());
    }
  }
}
