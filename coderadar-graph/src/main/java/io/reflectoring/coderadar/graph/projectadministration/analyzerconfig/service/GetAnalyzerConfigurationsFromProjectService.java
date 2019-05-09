package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.service;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.analyzerconfig.GetAnalyzerConfigurationsFromProjectPort;
import io.reflectoring.coderadar.graph.exception.ProjectNotFoundException;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.GetAnalyzerConfigurationsFromProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("GetAnalyzerConfigurationsFromProjectServiceNeo4j")
public class GetAnalyzerConfigurationsFromProjectService
    implements GetAnalyzerConfigurationsFromProjectPort {
  private final GetProjectRepository getProjectRepository;
  private final GetAnalyzerConfigurationsFromProjectRepository
      getAnalyzerConfigurationsFromProjectRepository;

  @Autowired
  public GetAnalyzerConfigurationsFromProjectService(
      GetProjectRepository getProjectRepository,
      GetAnalyzerConfigurationsFromProjectRepository
          getAnalyzerConfigurationsFromProjectRepository) {
    this.getProjectRepository = getProjectRepository;
    this.getAnalyzerConfigurationsFromProjectRepository =
        getAnalyzerConfigurationsFromProjectRepository;
  }

  @Override
  public List<AnalyzerConfiguration> get(Long id) {
    Optional<Project> persistedProject = getProjectRepository.findById(id);

    if (persistedProject.isPresent()) {
      return getAnalyzerConfigurationsFromProjectRepository.findByProject_Id(id);
    } else {
      throw new ProjectNotFoundException(
          "No analyzer configurations can be listed from a non-existing project.");
    }
  }
}
