package io.reflectoring.coderadar.dependencymap.service;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.dependencymap.domain.CompareNode;
import io.reflectoring.coderadar.dependencymap.port.driven.GetCompareTreePort;
import io.reflectoring.coderadar.dependencymap.port.driver.GetCompareTreeUseCase;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetCompareTreeService implements GetCompareTreeUseCase {
  private final GetCompareTreePort getCompareTreePort;
  private final GetProjectPort getProjectPort;
  private final CoderadarConfigurationProperties coderadarConfigurationProperties;

  @Autowired
  public GetCompareTreeService(
      GetCompareTreePort getCompareTreePort,
      GetProjectPort getProjectPort,
      CoderadarConfigurationProperties coderadarConfigurationProperties) {
    this.getCompareTreePort = getCompareTreePort;
    this.getProjectPort = getProjectPort;
    this.coderadarConfigurationProperties = coderadarConfigurationProperties;
  }

  @Override
  public CompareNode getDependencyTree(Long projectId, String commitName, String secondCommit) {
    Project project = getProjectPort.get(projectId);
    String projectRoot =
        coderadarConfigurationProperties.getWorkdir() + "/projects/" + project.getWorkdirName();
    return getCompareTreePort.getRoot(projectRoot, commitName, project.getName(), secondCommit);
  }
}
