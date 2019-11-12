package io.reflectoring.coderadar.vcs.port.driver;

import io.reflectoring.coderadar.vcs.UnableToGetCommitContentException;
import java.util.HashMap;
import java.util.List;

public interface GetCommitRawContentUseCase {

  /**
   * Returns the raw content of a commit.
   *
   * @param filepath The path of the local repository.
   * @param name The name of the commit
   * @return The raw commit data.
   */
  byte[] getCommitContent(String projectRoot, String filepath, String name)
      throws UnableToGetCommitContentException;

  /**
   * Returns the raw content of a list of files in a commit.
   *
   * @param filepaths The list of paths of the local repository.
   * @param name The name of the commit
   * @return The raw commit data grouped by the file.
   */
  HashMap<String, byte[]> getCommitContentBulk(
      String projectRoot, List<String> filepaths, String name)
      throws UnableToGetCommitContentException;
}
