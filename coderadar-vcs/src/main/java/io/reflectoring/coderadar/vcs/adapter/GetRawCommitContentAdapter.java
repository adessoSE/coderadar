package io.reflectoring.coderadar.vcs.adapter;

import io.reflectoring.coderadar.vcs.UnableToGetCommitContentException;
import io.reflectoring.coderadar.vcs.port.driven.GetRawCommitContentPort;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.ObjectId;
import org.gitective.core.BlobUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class GetRawCommitContentAdapter implements GetRawCommitContentPort {
  @Override
  public byte[] getCommitContent(String filepath, String name)
      throws UnableToGetCommitContentException {
    try {
      Git git = Git.open(new File(filepath)); // TODO: Filepath is not the repository root!!
      ObjectId commitId = git.getRepository().resolve(name);
      git.close();
      return BlobUtils.getRawContent(git.getRepository(), commitId, filepath);
    } catch (IOException e) {
      throw new UnableToGetCommitContentException(e.getMessage());
    }
  }
}
