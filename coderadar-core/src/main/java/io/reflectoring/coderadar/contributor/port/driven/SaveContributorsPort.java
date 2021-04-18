package io.reflectoring.coderadar.contributor.port.driven;

import io.reflectoring.coderadar.domain.Contributor;
import java.util.List;

public interface SaveContributorsPort {
  /**
   * @param contributors The contributors to save.
   * @param projectId The project id.
   */
  void save(List<Contributor> contributors, long projectId);
}
