package io.reflectoring.coderadar.query.service;

import io.reflectoring.coderadar.analyzer.MisconfigurationException;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.ListFilePatternsOfProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.query.domain.ContributorsForFile;
import io.reflectoring.coderadar.query.port.driven.GetCriticalFilesPort;
import io.reflectoring.coderadar.query.port.driver.GetCriticalFilesUseCase;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetCriticalFilesService implements GetCriticalFilesUseCase {
  private final GetCriticalFilesPort getCriticalFilesPort;
  private final GetProjectPort getProjectPort;
  private final ListFilePatternsOfProjectPort listFilePatternsOfProjectPort;

  public GetCriticalFilesService(
      GetCriticalFilesPort getCriticalFilesPort,
      GetProjectPort getProjectPort,
      ListFilePatternsOfProjectPort listFilePatternsOfProjectPort) {
    this.getCriticalFilesPort = getCriticalFilesPort;
    this.getProjectPort = getProjectPort;
    this.listFilePatternsOfProjectPort = listFilePatternsOfProjectPort;
  }

  @Override
  public List<ContributorsForFile> getCriticalFiles(Long projectId, int numberOfContributors) {
    if (!getProjectPort.existsById(projectId)) {
      throw new ProjectNotFoundException(projectId);
    }
    List<FilePattern> filePatterns = listFilePatternsOfProjectPort.listFilePatterns(projectId);
    if (filePatterns.isEmpty()) {
      throw new MisconfigurationException("No file patterns defined for this project");
    }
    return getCriticalFilesPort.getCriticalFiles(
        projectId, numberOfContributors, filePatterns);
  }
}
