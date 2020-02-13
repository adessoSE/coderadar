package io.reflectoring.coderadar.contributor.service;

import io.reflectoring.coderadar.contributor.domain.Contributor;
import io.reflectoring.coderadar.contributor.port.driven.ComputeContributorsPort;
import io.reflectoring.coderadar.contributor.port.driven.SaveContributorsPort;
import io.reflectoring.coderadar.contributor.port.driver.GetContributorsUseCase;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetContributorsService implements GetContributorsUseCase {
  private final ComputeContributorsPort computeContributorsPort;
  private final GetProjectPort getProjectPort;
  private final SaveContributorsPort saveContributorsPort;

  public GetContributorsService(
      ComputeContributorsPort computeContributorsPort,
      GetProjectPort getProjectPort,
      SaveContributorsPort saveContributorsPort) {
    this.computeContributorsPort = computeContributorsPort;
    this.getProjectPort = getProjectPort;
    this.saveContributorsPort = saveContributorsPort;
  }

  @Override
  public List<Contributor> getContributors(Long projectId) {
    Project project = getProjectPort.get(projectId);
    List<Contributor> contributors =
        computeContributorsPort.computeContributors(projectId, project.getWorkdirName());
    saveContributorsPort.save(contributors);
    return contributors;
  }
}
