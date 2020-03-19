package io.reflectoring.coderadar.contributor.port.driven;

import io.reflectoring.coderadar.contributor.domain.Contributor;
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
   * @param filename The filename we are looking for.
   * @return list of contributors that have made changes to the file with the given filename
   */
  List<Contributor> listAllByProjectIdAndFilepathInCommit(
      long projectId, String commitHash, String filename);
}
