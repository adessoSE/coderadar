package io.reflectoring.coderadar.graph.projectadministration.project.service;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.graph.analyzer.domain.CommitEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.DeleteProjectRepository;
import io.reflectoring.coderadar.graph.query.repository.DeleteCommitsRepository;
import io.reflectoring.coderadar.graph.query.repository.GetCommitsInProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.UnableToDeleteProjectException;
import io.reflectoring.coderadar.projectadministration.port.driven.project.DeleteProjectPort;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class DeleteProjectAdapter implements DeleteProjectPort {

  private final DeleteProjectRepository deleteProjectRepository;
  private final CoderadarConfigurationProperties coderadarConfigurationProperties;
  private final GetCommitsInProjectRepository getCommitsInProjectRepository;
  private final DeleteCommitsRepository deleteCommitsRepository;

  private final Logger logger = LoggerFactory.getLogger(DeleteProjectAdapter.class);

  @Autowired
  public DeleteProjectAdapter(
      DeleteProjectRepository deleteProjectRepository,
      CoderadarConfigurationProperties coderadarConfigurationProperties,
      GetCommitsInProjectRepository getCommitsInProjectRepository,
      DeleteCommitsRepository deleteCommitsRepository) {
    this.deleteProjectRepository = deleteProjectRepository;
    this.coderadarConfigurationProperties = coderadarConfigurationProperties;
    this.getCommitsInProjectRepository = getCommitsInProjectRepository;
    this.deleteCommitsRepository = deleteCommitsRepository;
  }

  @Override
  public void delete(Long id) {
    ProjectEntity projectEntity =
        deleteProjectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));
    try {
      List<CommitEntity> commitEntities = getCommitsInProjectRepository.findByProjectId(id);
      commitEntities.forEach(deleteCommitsRepository::delete);
      deleteProjectRepository.deleteProjectCascade(id);
      deleteWorkdir(projectEntity);

    } catch (IllegalArgumentException e) {
      throw new UnableToDeleteProjectException(id);
    }
  }

  /**
   * Deletes the working directory of the project.
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
      logger.error(String.format("Could not delete project working directory %s", projectEntity.getWorkdirName()));
    }
  }
}
