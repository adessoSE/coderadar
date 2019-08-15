package io.reflectoring.coderadar.projectadministration.project;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.projectadministration.ProjectAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.UpdateCommitsPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.ProjectStatusPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.UpdateProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.project.update.UpdateProjectCommand;
import io.reflectoring.coderadar.projectadministration.service.ProcessProjectService;
import io.reflectoring.coderadar.projectadministration.service.project.UpdateProjectService;
import io.reflectoring.coderadar.vcs.port.driver.GetProjectCommitsUseCase;
import io.reflectoring.coderadar.vcs.port.driver.UpdateRepositoryUseCase;
import java.util.Collections;
import java.util.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.scheduling.TaskScheduler;

class UpdateProjectServiceTest {
  private GetProjectPort getProjectPort = mock(GetProjectPort.class);
  private UpdateProjectPort updateProjectPort = mock(UpdateProjectPort.class);
  private UpdateRepositoryUseCase updateRepositoryUseCase = mock(UpdateRepositoryUseCase.class);
  private CoderadarConfigurationProperties coderadarConfigurationProperties =
      mock(CoderadarConfigurationProperties.class);
  private ProcessProjectService processProjectService = mock(ProcessProjectService.class);
  private UpdateCommitsPort updateCommitsPort = mock(UpdateCommitsPort.class);
  private GetProjectCommitsUseCase getProjectCommitsUseCase = mock(GetProjectCommitsUseCase.class);
  private ProjectStatusPort projectStatusPort = mock(ProjectStatusPort.class);
  private TaskScheduler taskScheduler = mock(TaskScheduler.class);

  @Test
  void updateProjectReturnsErrorWhenProjectWithNameAlreadyExists() {
    UpdateProjectService testSubject =
        new UpdateProjectService(
            getProjectPort,
            updateProjectPort,
            updateRepositoryUseCase,
            coderadarConfigurationProperties,
            processProjectService,
            getProjectCommitsUseCase,
            updateCommitsPort,
            projectStatusPort,
            taskScheduler);

    UpdateProjectCommand command =
        new UpdateProjectCommand(
            "new project name",
            "username",
            "password",
            "http://valid.url",
            true,
            new Date(),
            new Date());

    Project project = new Project();
    project.setId(1L);
    project.setName("new project name");

    Project project2 = new Project();
    project2.setId(2L);
    project2.setName("new project name");

    Mockito.when(getProjectPort.findByName(project.getName()))
        .thenReturn(Collections.singletonList(project2));

    Assertions.assertThrows(
        ProjectAlreadyExistsException.class, () -> testSubject.update(command, 1L));
  }
}
