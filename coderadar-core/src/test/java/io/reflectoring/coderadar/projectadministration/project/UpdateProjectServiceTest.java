package io.reflectoring.coderadar.projectadministration.project;

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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.scheduling.TaskScheduler;

@ExtendWith(MockitoExtension.class)
class UpdateProjectServiceTest {

  @Mock private GetProjectPort getProjectPort;

  @Mock private UpdateProjectPort updateProjectPort;

  @Mock private UpdateRepositoryUseCase updateRepositoryUseCase;

  @Mock private CoderadarConfigurationProperties coderadarConfigurationProperties;

  @Mock private ProcessProjectService processProjectService;

  @Mock private GetProjectCommitsUseCase getProjectCommitsUseCase;

  @Mock private UpdateCommitsPort updateCommitsPort;

  @Mock private ProjectStatusPort projectStatusPort;

  @Mock private TaskScheduler taskScheduler;

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
