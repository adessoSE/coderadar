package io.reflectoring.coderadar.query.service;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.query.port.driven.GetAuthorsPort;
import io.reflectoring.coderadar.query.port.driver.GetAuthorsUseCase;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetAuthorsService implements GetAuthorsUseCase {
  private final GetAuthorsPort getAuthorsPort;
  private final GetProjectPort getProjectPort;

  public GetAuthorsService(GetAuthorsPort getAuthorsPort, GetProjectPort getProjectPort) {
    this.getAuthorsPort = getAuthorsPort;
    this.getProjectPort = getProjectPort;
  }

  @Override
  public List<String> getNumberOfAuthors(Long projectId) {
    if (!getProjectPort.existsById(projectId)) {
      throw new ProjectNotFoundException(projectId);
    }
    return getAuthorsPort.getNumberOfAuthors(projectId);
  }
}
