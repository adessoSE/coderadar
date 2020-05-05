package io.reflectoring.coderadar.graph.projectadministration.project.adapter;

import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.UpdateProjectPort;
import org.springframework.stereotype.Service;

@Service
public class UpdateProjectAdapter implements UpdateProjectPort {
  private final ProjectRepository projectRepository;

  public UpdateProjectAdapter(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public void update(Project project) {
    projectRepository.save(
        projectRepository
            .findById(project.getId())
            .orElseThrow(() -> new ProjectNotFoundException(project.getId()))
            .setName(project.getName())
            .setVcsStart(project.getVcsStart())
            .setVcsOnline(project.isVcsOnline())
            .setVcsUsername(project.getVcsUsername())
            .setVcsPassword(project.getVcsPassword())
            .setVcsUrl(project.getVcsUrl())
            .setWorkdirName(project.getWorkdirName()));
  }
}
