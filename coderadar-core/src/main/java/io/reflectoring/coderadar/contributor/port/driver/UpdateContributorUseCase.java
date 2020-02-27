package io.reflectoring.coderadar.contributor.port.driver;

public interface UpdateContributorUseCase {
  void updateContributor(Long id, UpdateContributorCommand command);
}
