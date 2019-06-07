package io.reflectoring.coderadar.graph.projectadministration.project.service;

import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.DeleteAnalyzerConfigurationRepository;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.DeleteFilePatternRepository;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.DeleteModuleRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.DeleteProjectRepository;
import io.reflectoring.coderadar.graph.query.repository.GetCommitsInProjectRepository;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.DeleteProjectPort;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteProjectAdapter implements DeleteProjectPort {

  private final DeleteProjectRepository deleteProjectRepository;
  private final DeleteModuleRepository deleteModuleRepository;
  private final DeleteFilePatternRepository deleteFilePatternRepository;
  private final DeleteAnalyzerConfigurationRepository deleteAnalyzerConfigurationRepository;
  private final GetCommitsInProjectRepository getCommitsInProjectRepository;

  @Autowired
  public DeleteProjectAdapter(
      DeleteProjectRepository deleteProjectRepository,
      DeleteModuleRepository deleteModuleRepository,
      DeleteFilePatternRepository deleteFilePatternRepository,
      DeleteAnalyzerConfigurationRepository deleteAnalyzerConfigurationRepository,
      GetCommitsInProjectRepository getCommitsInProjectRepository) {
    this.deleteProjectRepository = deleteProjectRepository;
    this.deleteModuleRepository = deleteModuleRepository;
    this.deleteFilePatternRepository = deleteFilePatternRepository;
    this.deleteAnalyzerConfigurationRepository = deleteAnalyzerConfigurationRepository;
    this.getCommitsInProjectRepository = getCommitsInProjectRepository;
  }

  @Override
  public void delete(Long id) {
    Optional<Project> project = deleteProjectRepository.findById(id);
    if (project.isPresent()) {
      deleteAnalyzerConfigurationRepository.deleteAll(project.get().getAnalyzerConfigurations());
      deleteModuleRepository.deleteAll(project.get().getModules());
      deleteFilePatternRepository.deleteAll(project.get().getFilePatterns());
      getCommitsInProjectRepository.deleteAll(project.get().getCommits());
      deleteProjectRepository.deleteById(id);
    }
  }

  @Override
  public void delete(Project project) {
    deleteAnalyzerConfigurationRepository.deleteAll(project.getAnalyzerConfigurations());
    deleteModuleRepository.deleteAll(project.getModules());
    deleteFilePatternRepository.deleteAll(project.getFilePatterns());
    getCommitsInProjectRepository.deleteAll(project.getCommits());
    deleteProjectRepository.delete(project);
  }
}
