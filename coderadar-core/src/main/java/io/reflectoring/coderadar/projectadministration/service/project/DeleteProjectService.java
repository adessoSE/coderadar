package io.reflectoring.coderadar.projectadministration.service.project;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.DeleteProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.project.delete.DeleteProjectUseCase;
import io.reflectoring.coderadar.projectadministration.service.ProcessProjectService;
import io.reflectoring.coderadar.vcs.port.driven.DeleteLocalRepositoryPort;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteProjectService implements DeleteProjectUseCase {

  private final DeleteProjectPort deleteProjectPort;
  private final ProcessProjectService processProjectService;
  private final GetProjectPort getProjectPort;
  private final DeleteLocalRepositoryPort deleteLocalRepositoryPort;
  private final CoderadarConfigurationProperties coderadarConfigurationProperties;

  private static final Logger logger = LoggerFactory.getLogger(DeleteProjectService.class);

  @Override
  public void delete(long id) {
    Project project = getProjectPort.get(id);
    processProjectService.executeTask(
        () -> {
          deleteProjectPort.delete(id);
          try {
            deleteLocalRepositoryPort.deleteRepository(
                coderadarConfigurationProperties.getWorkdir()
                    + "/projects/"
                    + project.getWorkdirName());
            logger.info("Deleted project {} with id {}", project.getName(), id);
          } catch (IOException e) {
            logger.error(
                "Unable to delete local repository for project {}, {}",
                project.getName(),
                e.getMessage());
          }
        },
        id);
  }
}
