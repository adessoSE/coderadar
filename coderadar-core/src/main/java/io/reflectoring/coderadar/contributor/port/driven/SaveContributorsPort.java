package io.reflectoring.coderadar.contributor.port.driven;

import io.reflectoring.coderadar.contributor.domain.Contributor;
import java.util.List;

public interface SaveContributorsPort {
  void save(List<Contributor> contributors, Long projectId);
}
