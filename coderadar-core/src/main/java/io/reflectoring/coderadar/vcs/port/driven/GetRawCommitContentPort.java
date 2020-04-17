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


  byte[] getFileDiff(String projectRoot, String filepath, String name)
          throws UnableToGetCommitContentException;

  /**
   * Returns the raw content of a list of files in a commit.
   *
   * @param filepaths The list of paths of the local repository.
   * @param name The name of the commit
   * @return The raw commit data grouped by the filepath.
   */
  HashMap<String, byte[]> getCommitContentBulk(
      String projectRoot, List<String> filepaths, String name)
      throws UnableToGetCommitContentException;

  /**
   * Returns the raw content of a list of files in a commit.
   *
   * @param files The list of files in the commit.
   * @param name The name of the commit
   * @return The raw commit data grouped by the file.
   */
  HashMap<io.reflectoring.coderadar.projectadministration.domain.File, byte[]>
      getCommitContentBulkWithFiles(
          String projectRoot,
          List<io.reflectoring.coderadar.projectadministration.domain.File> files,
          String name)
          throws UnableToGetCommitContentException;
}
