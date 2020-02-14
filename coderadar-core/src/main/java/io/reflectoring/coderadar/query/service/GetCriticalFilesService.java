package io.reflectoring.coderadar.query.service;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.query.port.driven.GetCriticalFilesPort;
import io.reflectoring.coderadar.query.port.driver.GetCriticalFilesUseCase;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetCriticalFilesService implements GetCriticalFilesUseCase {
  private final GetCriticalFilesPort getCriticalFilesPort;
  private final GetProjectPort getProjectPort;

  public GetCriticalFilesService(
      GetCriticalFilesPort getCriticalFilesPort, GetProjectPort getProjectPort) {
    this.getCriticalFilesPort = getCriticalFilesPort;
    this.getProjectPort = getProjectPort;
  }

  @Override
  public List<String> getCriticalFiles(Long projectId) {
    if (!getProjectPort.existsById(projectId)) {
      throw new ProjectNotFoundException(projectId);
    }
    return getCriticalFilesPort.getCriticalFiles(projectId);
  }
}
