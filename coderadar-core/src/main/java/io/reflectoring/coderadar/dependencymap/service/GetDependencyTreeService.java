package io.reflectoring.coderadar.dependencymap.service;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.dependencymap.domain.Node;
import io.reflectoring.coderadar.dependencymap.port.driven.GetDependencyTreePort;
import io.reflectoring.coderadar.dependencymap.port.driver.GetDependencyTreeUseCase;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetDependencyTreeService implements GetDependencyTreeUseCase {

  private final GetDependencyTreePort getDependencyTreePort;
  private final GetProjectPort getProjectPort;
  private final CoderadarConfigurationProperties coderadarConfigurationProperties;

  @Autowired
  public GetDependencyTreeService(
      GetDependencyTreePort getDependencyTreePort,
      GetProjectPort getProjectPort,
      CoderadarConfigurationProperties coderadarConfigurationProperties) {
    this.getDependencyTreePort = getDependencyTreePort;
    this.getProjectPort = getProjectPort;
    this.coderadarConfigurationProperties = coderadarConfigurationProperties;
  }

  @Override
  public Node getDependencyTree(Long projectId, String commitName) {
    Project project = getProjectPort.get(projectId);
    String projectRoot =
        coderadarConfigurationProperties.getWorkdir() + "/projects/" + project.getWorkdirName();
    return getDependencyTreePort.getRoot(projectRoot, commitName, project.getName());
  }
}
