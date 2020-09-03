package io.reflectoring.coderadar.contributor.service;

import io.reflectoring.coderadar.ValidationUtils;
import io.reflectoring.coderadar.contributor.domain.Contributor;
import io.reflectoring.coderadar.contributor.port.driven.ListContributorsPort;
import io.reflectoring.coderadar.contributor.port.driver.GetContributorsForPathCommand;
import io.reflectoring.coderadar.contributor.port.driver.ListContributorsUseCase;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ListContributorsService implements ListContributorsUseCase {
  private final GetProjectPort getProjectPort;
  private final ListContributorsPort listContributorsPort;

  public ListContributorsService(
      GetProjectPort getProjectPort, ListContributorsPort listContributorsPort) {
    this.getProjectPort = getProjectPort;
    this.listContributorsPort = listContributorsPort;
  }

  @Override
  public List<Contributor> listContributors(long projectId) {
    checkProjectExists(projectId);
    return listContributorsPort.listAllByProjectId(projectId);
  }

  @Override
  public List<Contributor> listContributorsForProjectAndPathInCommit(
      long projectId, GetContributorsForPathCommand command) {
    checkProjectExists(projectId);
    return listContributorsPort.listAllByProjectIdAndPathInCommit(
        projectId,
        ValidationUtils.validateAndTrimCommitHash(command.getCommitHash()),
        command.getPath());
  }

  private void checkProjectExists(long projectId) {
    if (!getProjectPort.existsById(projectId)) {
      throw new ProjectNotFoundException(projectId);
    }
  }
}
