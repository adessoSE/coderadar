package io.reflectoring.coderadar.graph.projectadministration.project.adapter;

import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.port.driven.project.DeleteProjectPort;
import org.springframework.stereotype.Service;

@Service
public class DeleteProjectAdapter implements DeleteProjectPort {

  private final ProjectRepository projectRepository;

  public DeleteProjectAdapter(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public void delete(Long projectId) {
    projectRepository.setBeingDeleted(projectId, true);

    /*
     * The empty while loops are necessary because only 10000 entities can be deleted at a time.
     * @see ProjectRepository#deleteProjectFindings(Long)
     * @see ProjectRepository#deleteProjectMetrics(Long)
     */
    while (projectRepository.deleteProjectFindings(projectId) > 0) ;
    while (projectRepository.deleteProjectMetrics(projectId) > 0) ;
    while (projectRepository.deleteProjectFilesAndModules(projectId) > 0) ;
    projectRepository.deleteProjectCommits(projectId);
    projectRepository.deleteProjectConfiguration(projectId);
    projectRepository.deleteById(projectId);
  }
}
