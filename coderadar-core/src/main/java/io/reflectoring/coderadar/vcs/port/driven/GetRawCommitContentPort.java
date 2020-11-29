package io.reflectoring.coderadar.vcs.port.driven;

import io.reflectoring.coderadar.analyzer.domain.AnalyzeFileDto;
import java.util.HashMap;
import java.util.List;
import org.springframework.data.util.Pair;

public interface GetRawCommitContentPort {

  /**
   * Returns the raw content of a commit.
   *
   * @param projectRoot The local repository.
   * @param filepath The path of the file.
   * @param commitHash The commitHash
   * @return The raw commit data.
   */
  byte[] getCommitContent(String projectRoot, String filepath, String commitHash);

  /**
   * @param projectRoot The local repository.
   * @param filepath The path of the file.
   * @param commitHash The commit hash
   * @return The diff against the same file in the parent commit or an empty array if the file does
   *     not exist
   */
  byte[] getFileDiff(String projectRoot, String filepath, String commitHash);

  /**
   * Returns the raw content of a list of files in a commit.
   *
   * @param projectRoot The local repository.
   * @param filepaths The list of paths of the local repository.
   * @param commitHash The commit hash
   * @return The raw commit data grouped by the filepath.
   */
  HashMap<String, byte[]> getCommitContentBulk(
      String projectRoot, List<String> filepaths, String commitHash);

  /**
   * Returns the raw content of a list of files in a commit.
   *
   * @param projectRoot The local repository.
   * @param files The list of files in the commit.
   * @param commitHash The commit hash
   * @return The raw commit data grouped by the file.
   */
  HashMap<AnalyzeFileDto, byte[]> getCommitContentBulkWithFiles(
      String projectRoot, AnalyzeFileDto[] files, String commitHash);

  /**
   * @param parentHash The first commit hash.
   * @param commitHash The second commit hash.
   * @param projectRoot The local repository.
   * @return A list of String pairs, where the left element is the old path and the right one is the
   *     new path.
   */
  List<Pair<String, String>> getRenamesBetweenCommits(
      String parentHash, String commitHash, String projectRoot);
}
