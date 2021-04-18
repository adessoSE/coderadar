package io.reflectoring.coderadar.contributor.port.driven;

import io.reflectoring.coderadar.domain.Contributor;
import java.util.List;

public interface ListContributorsPort {
  /** @return all contributors */
  List<Contributor> listAll();

  /**
   * @param projectId The project id.
   * @return list of contributors who work on the corresponding project
   */
  List<Contributor> listAllByProjectId(long projectId);

  /**
   * @param projectId The project id.
   * @param commitHash Only search for critical files in the file tree of the given commit.
   * @param path The path to look for. It can be a directory or a filepath.
   * @return The list of contributors that worked on the given file/directory.
   */
  List<Contributor> listAllByProjectIdAndPathInCommit(long projectId, long commitHash, String path);
}
