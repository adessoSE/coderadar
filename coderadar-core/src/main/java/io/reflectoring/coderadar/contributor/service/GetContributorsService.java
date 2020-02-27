package io.reflectoring.coderadar.contributor.service;

import io.reflectoring.coderadar.contributor.domain.Contributor;
import io.reflectoring.coderadar.contributor.port.driven.GetContributorPort;
import io.reflectoring.coderadar.contributor.port.driver.GetContributorsUseCase;
import io.reflectoring.coderadar.contributor.port.driver.GetForFilenameCommand;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetContributorsService implements GetContributorsUseCase {
  private final GetProjectPort getProjectPort;
  private final GetContributorPort getContributorPort;

  public GetContributorsService(
      GetProjectPort getProjectPort, GetContributorPort getContributorPort) {
    this.getProjectPort = getProjectPort;
    this.getContributorPort = getContributorPort;
  }

  @Override
  public List<Contributor> getContributors(Long projectId) {
    if (!getProjectPort.existsById(projectId)) {
      throw new ProjectNotFoundException(projectId);
    }
    return getContributorPort.findAllByProjectId(projectId);
  }

  @Override
  public List<Contributor> getContributorsForProjectAndFilename(
      Long projectId, GetForFilenameCommand command) {
    if (!getProjectPort.existsById(projectId)) {
      throw new ProjectNotFoundException(projectId);
    }
    return getContributorPort.findAllByProjectIdAndFilename(projectId, command.getFilename());
  }
}
