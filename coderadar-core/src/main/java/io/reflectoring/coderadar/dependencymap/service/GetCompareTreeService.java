package io.reflectoring.coderadar.dependencymap.service;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.dependencymap.domain.CompareNode;
import io.reflectoring.coderadar.dependencymap.port.driven.GetCompareTreePort;
import io.reflectoring.coderadar.dependencymap.port.driver.GetCompareTreeUseCase;
import io.reflectoring.coderadar.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCompareTreeService implements GetCompareTreeUseCase {
  private final GetCompareTreePort getCompareTreePort;
  private final GetProjectPort getProjectPort;
  private final CoderadarConfigurationProperties coderadarConfigurationProperties;

  @Override
  public CompareNode getDependencyTree(Long projectId, String commitName, String secondCommit) {
    Project project = getProjectPort.get(projectId);
    String projectRoot =
        coderadarConfigurationProperties.getWorkdir() + "/projects/" + project.getWorkdirName();
    return getCompareTreePort.getRoot(projectRoot, commitName, project.getName(), secondCommit);
  }
}
