package io.reflectoring.coderadar.useradministration.service.delete;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.analyzer.port.driver.StopAnalyzingUseCase;
import io.reflectoring.coderadar.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.DeleteProjectPort;
import io.reflectoring.coderadar.projectadministration.service.ProcessProjectService;
import io.reflectoring.coderadar.projectadministration.service.project.ScanProjectScheduler;
import io.reflectoring.coderadar.useradministration.UserNotFoundException;
import io.reflectoring.coderadar.useradministration.port.driven.DeleteUserPort;
import io.reflectoring.coderadar.useradministration.port.driven.GetUserPort;
import io.reflectoring.coderadar.useradministration.port.driven.ListProjectsForUserPort;
import io.reflectoring.coderadar.useradministration.port.driver.delete.DeleteUserUseCase;
import io.reflectoring.coderadar.vcs.port.driven.DeleteLocalRepositoryPort;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserService implements DeleteUserUseCase {

  private final GetUserPort getUserPort;
  private final DeleteUserPort deleteUserPort;
  private final StopAnalyzingUseCase stopAnalyzingUseCase;
  private final ListProjectsForUserPort listProjectsForUserPort;
  private final DeleteProjectPort deleteProjectPort;
  private final DeleteLocalRepositoryPort deleteLocalRepositoryPort;
  private final CoderadarConfigurationProperties coderadarConfigurationProperties;
  private final ScanProjectScheduler scanProjectScheduler;
  private final ProcessProjectService processProjectService;

  private static final Logger logger = LoggerFactory.getLogger(DeleteUserService.class);

  @SneakyThrows
  @Override
  public void deleteUser(long userId) {
    if (getUserPort.existsById(userId)) {
      logger.info("Deleted user with id: {}", userId);
      List<Project> projectsForUser = listProjectsForUserPort.listProjectsCreatedByUser(userId);
      for (Project project : projectsForUser) {
        stopAnalyzingUseCase.stop(project.getId());
        processProjectService.waitForProjectTasks(project.getId());
        scanProjectScheduler.stopUpdateTask(project.getId());
        deleteProjectPort.delete(project.getId());
        try {
          deleteLocalRepositoryPort.deleteRepository(
              coderadarConfigurationProperties.getWorkdir()
                  + "/projects/"
                  + project.getWorkdirName());
          logger.info("Deleted project {} with id {}", project.getName(), project.getId());
        } catch (IOException e) {
          logger.error(
              "Unable to delete local repository for project {}, {}",
              project.getName(),
              e.getMessage());
        }
      }
      deleteUserPort.deleteUser(userId);
    } else {
      throw new UserNotFoundException(userId);
    }
  }
}
