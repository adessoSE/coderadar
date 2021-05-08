package io.reflectoring.coderadar.graph.projectadministration.project.adapter;

import io.reflectoring.coderadar.domain.Project;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.project.UpdateProjectPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateProjectAdapter implements UpdateProjectPort {
  private final ProjectRepository projectRepository;

  @Override
  public void update(Project project) {
    projectRepository.save(
        projectRepository
            .findById(project.getId())
            .orElseThrow(() -> new ProjectNotFoundException(project.getId()))
            .setName(project.getName())
            .setVcsStart(project.getVcsStart())
            .setVcsUsername(project.getVcsUsername())
            .setVcsPassword(project.getVcsPassword())
            .setVcsUrl(project.getVcsUrl())
            .setDefaultBranch(project.getDefaultBranch())
            .setWorkdirName(project.getWorkdirName()));
  }
}
