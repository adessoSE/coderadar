package io.reflectoring.coderadar.contributor.port.driven;

import io.reflectoring.coderadar.contributor.port.driver.UpdateContributorCommand;

public interface UpdateContributorPort {
  void updateContributor(Long id, UpdateContributorCommand command);
}
