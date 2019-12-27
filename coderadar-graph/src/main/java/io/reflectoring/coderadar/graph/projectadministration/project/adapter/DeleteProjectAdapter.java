package io.reflectoring.coderadar.graph.projectadministration.project.adapter;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.DeleteProjectPort;
import java.io.File;
import java.io.IOException;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DeleteProjectAdapter implements DeleteProjectPort {

  private final ProjectRepository projectRepository;
  private final CoderadarConfigurationProperties coderadarConfigurationProperties;

  private final Logger logger = LoggerFactory.getLogger(DeleteProjectAdapter.class);

  public DeleteProjectAdapter(
      ProjectRepository projectRepository,
      CoderadarConfigurationProperties coderadarConfigurationProperties) {
    this.projectRepository = projectRepository;
    this.coderadarConfigurationProperties = coderadarConfigurationProperties;
  }

  @Override
  public void delete(Project project) {
    projectRepository.setBeingDeleted(project.getId(), true);

    /*
     * The empty while loops are necessary because only 10000 entities can be deleted at a time.
     * @see ProjectRepository#deleteProjectFindings(Long)
     * @see ProjectRepository#deleteProjectMetrics(Long)
     */
    while (projectRepository.deleteProjectFindings(project.getId()) > 0) ;
    while (projectRepository.deleteProjectMetrics(project.getId()) > 0) ;
    projectRepository.deleteProjectFilesAndModules(project.getId());
    projectRepository.deleteProjectCommits(project.getId());
    projectRepository.deleteProjectConfiguration(project.getId());
    projectRepository.deleteById(project.getId());
    deleteWorkdir(project.getWorkdirName());
  }

  /**
   * Deletes the working directory of the project.
   *
   * @param workdir The directory to delete.
   */
  private void deleteWorkdir(String workdir) {
    try {
      FileUtils.deleteDirectory(
          new File(coderadarConfigurationProperties.getWorkdir() + "/projects/" + workdir));
    } catch (IOException e) {
      logger.error(String.format("Could not delete project working directory %s", workdir));
    }
  }
}
