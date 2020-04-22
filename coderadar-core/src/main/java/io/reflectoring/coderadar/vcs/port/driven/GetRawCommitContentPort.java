package io.reflectoring.coderadar.vcs.port.driven;

import io.reflectoring.coderadar.vcs.UnableToGetCommitContentException;
import java.util.HashMap;
import java.util.List;

public interface GetRawCommitContentPort {

  /**
   * Returns the raw content of a commit.
   *
   * @param projectRoot The local repository.
   * @param filepath The path of the file.
   * @param commitHash The commitHash
   * @return The raw commit data.
   */
  byte[] getCommitContent(String projectRoot, String filepath, String commitHash)
      throws UnableToGetCommitContentException;

  /**
   * @param projectRoot The local repository.
   * @param filepath The path of the file.
   * @param commitHash The commit hash
   * @return The diff against the same file in the parent commit or an empty array if the file does
   *     not exist
   * @throws UnableToGetCommitContentException Thrown if the commit is not found.
   */
  byte[] getFileDiff(String projectRoot, String filepath, String commitHash)
      throws UnableToGetCommitContentException;

  /**
   * Returns the raw content of a list of files in a commit.
   *
   * @param projectRoot The local repository.
   * @param filepaths The list of paths of the local repository.
   * @param commitHash The commit hash
   * @return The raw commit data grouped by the filepath.
   */
  HashMap<String, byte[]> getCommitContentBulk(
      String projectRoot, List<String> filepaths, String commitHash)
      throws UnableToGetCommitContentException;

  /**
   * Returns the raw content of a list of files in a commit.
   *
   * @param projectRoot The local repository.
   * @param files The list of files in the commit.
   * @param commitHash The commit hash
   * @return The raw commit data grouped by the file.
   */
  HashMap<io.reflectoring.coderadar.projectadministration.domain.File, byte[]>
      getCommitContentBulkWithFiles(
          String projectRoot,
          List<io.reflectoring.coderadar.projectadministration.domain.File> files,
          String commitHash)
          throws UnableToGetCommitContentException;
}
