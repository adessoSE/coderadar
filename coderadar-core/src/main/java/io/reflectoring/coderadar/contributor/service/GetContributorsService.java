package io.reflectoring.coderadar.contributor.service;

import io.reflectoring.coderadar.contributor.domain.Contributor;
import io.reflectoring.coderadar.contributor.port.driven.ComputeContributorsPort;
import io.reflectoring.coderadar.contributor.port.driver.GetContributorsUseCase;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetContributorsService implements GetContributorsUseCase {
  private final ComputeContributorsPort computeContributorsPort;
  private final GetProjectPort getProjectPort;

  public GetContributorsService(
      ComputeContributorsPort computeContributorsPort, GetProjectPort getProjectPort) {
    this.computeContributorsPort = computeContributorsPort;
    this.getProjectPort = getProjectPort;
  }

  @Override
  public List<Contributor> getContributors(Long projectId) {
    Project project = getProjectPort.get(projectId);
    List<Contributor> contributors =
        computeContributorsPort.computeContributors(projectId, project.getWorkdirName());
    // todo: save the contributors in database
    return contributors;
  }
}
