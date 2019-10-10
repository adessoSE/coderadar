package io.reflectoring.coderadar.vcs.port.driven;

import io.reflectoring.coderadar.vcs.UnableToGetCommitContentException;

public interface GetRawCommitContentPort {

  /**
   * Returns the raw content of a commit.
   *
   * @param filepath The path of the local repository.
   * @param name The name of the commit
   * @return The raw commit data.
   */
  byte[] getCommitContent(String projectRoot, String filepath, String name)
      throws UnableToGetCommitContentException;
}
