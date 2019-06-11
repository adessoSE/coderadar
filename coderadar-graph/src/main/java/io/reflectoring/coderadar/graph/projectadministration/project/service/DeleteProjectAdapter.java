package io.reflectoring.coderadar.graph.projectadministration.project.service;

import io.reflectoring.coderadar.graph.projectadministration.project.repository.DeleteProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.DeleteProjectPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteProjectAdapter implements DeleteProjectPort {

  private final DeleteProjectRepository deleteProjectRepository;

  @Autowired
  public DeleteProjectAdapter(
      DeleteProjectRepository deleteProjectRepository) {
    this.deleteProjectRepository = deleteProjectRepository;
  }

  @Override
  public void delete(Long id) {
    deleteProjectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));
    deleteProjectRepository.deleteProjectCascade(id);
  }

  @Override
  public void delete(Project project) {
    deleteProjectRepository.findById(project.getId()).orElseThrow(() -> new ProjectNotFoundException(project.getId()));
    deleteProjectRepository.deleteProjectCascade(project.getId());
  }
}
