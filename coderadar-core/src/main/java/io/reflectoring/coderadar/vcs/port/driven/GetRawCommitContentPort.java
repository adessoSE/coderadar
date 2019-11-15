package io.reflectoring.coderadar.vcs.port.driven;

import io.reflectoring.coderadar.vcs.UnableToGetCommitContentException;
import java.util.HashMap;
import java.util.List;

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

  HashMap<String, byte[]> getCommitContentBulk(
      String projectRoot, List<String> filepaths, String name)
      throws UnableToGetCommitContentException;

  HashMap<io.reflectoring.coderadar.projectadministration.domain.File, byte[]>
      getCommitContentBulkWithFiles(
          String projectRoot,
          List<io.reflectoring.coderadar.projectadministration.domain.File> files,
          String name)
          throws UnableToGetCommitContentException;
}
