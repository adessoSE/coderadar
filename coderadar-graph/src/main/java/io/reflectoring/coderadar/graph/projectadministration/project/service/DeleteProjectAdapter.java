package io.reflectoring.coderadar.graph.projectadministration.project.service;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.UnableToDeleteProjectException;
import io.reflectoring.coderadar.projectadministration.port.driven.project.DeleteProjectPort;
import java.io.File;
import java.io.IOException;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteProjectAdapter implements DeleteProjectPort {

  private final ProjectRepository projectRepository;
  private final CoderadarConfigurationProperties coderadarConfigurationProperties;

  private final Logger logger = LoggerFactory.getLogger(DeleteProjectAdapter.class);

  @Autowired
  public DeleteProjectAdapter(
      ProjectRepository projectRepository,
      CoderadarConfigurationProperties coderadarConfigurationProperties) {
    this.projectRepository = projectRepository;
    this.coderadarConfigurationProperties = coderadarConfigurationProperties;
  }

  @Override
  public void delete(Long id) {
    ProjectEntity projectEntity =
        projectRepository.findProjectById(id).orElseThrow(() -> new ProjectNotFoundException(id));
    try {
      projectEntity.setBeingDeleted(true);
      projectRepository.save(projectEntity);

      projectRepository.deleteProjectFindings(id);
      projectRepository.deleteProjectMetrics(id);
      projectRepository.deleteProjectFilesAndModules(id);
      projectRepository.deleteProjectCommits(id);
      projectRepository.deleteProjectConfiguration(id);
      projectRepository.deleteById(id);
      deleteWorkdir(projectEntity);
    } catch (IllegalArgumentException e) {
      throw new UnableToDeleteProjectException(id);
    }
  }

  /**
   * Deletes the working directory of the project.
   *
   * @param projectEntity The project, whose directory to delete.
   */
  private void deleteWorkdir(ProjectEntity projectEntity) {
    try {
      FileUtils.deleteDirectory(
          new File(
              coderadarConfigurationProperties.getWorkdir()
                  + "/projects/"
                  + projectEntity.getWorkdirName()));
    } catch (IOException e) {
      logger.error(
          String.format(
              "Could not delete project working directory %s", projectEntity.getWorkdirName()));
    }
  }
}