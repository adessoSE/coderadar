package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.service;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.analyzerconfig.AddAnalyzerConfigurationPort;
import io.reflectoring.coderadar.graph.exception.ProjectNotFoundException;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.AddAnalyzerConfigurationRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddAnalyzerConfigurationService implements AddAnalyzerConfigurationPort {
  private final GetProjectRepository getProjectRepository;
  private final AddAnalyzerConfigurationRepository addAnalyzerConfigurationRepository;

  @Autowired
  public AddAnalyzerConfigurationService(
      GetProjectRepository getProjectRepository,
      AddAnalyzerConfigurationRepository addAnalyzerConfigurationRepository) {
    this.getProjectRepository = getProjectRepository;
    this.addAnalyzerConfigurationRepository = addAnalyzerConfigurationRepository;
  }

  @Override
  public Long add(Long projectId, AnalyzerConfiguration entity) {
    Optional<Project> persisted = getProjectRepository.findById(projectId);

    if (persisted.isPresent()) {
      entity.setProject(persisted.get());
      return addAnalyzerConfigurationRepository.save(entity).getId();
    } else {
      throw new ProjectNotFoundException(
          String.format(
              "There is no project with the ID %d. Creation of module failed.", projectId));
    }
  }
}
