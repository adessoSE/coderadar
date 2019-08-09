package io.reflectoring.coderadar.projectadministration.project;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.projectadministration.ProjectAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.UpdateProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.project.update.UpdateProjectCommand;
import io.reflectoring.coderadar.projectadministration.service.ProcessProjectService;
import io.reflectoring.coderadar.projectadministration.service.project.UpdateProjectService;
import io.reflectoring.coderadar.vcs.port.driver.UpdateRepositoryUseCase;
import java.util.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UpdateProjectServiceTest {
  private GetProjectPort getProjectPort = mock(GetProjectPort.class);
  private UpdateProjectPort updateProjectPort = mock(UpdateProjectPort.class);
  private UpdateRepositoryUseCase updateRepositoryUseCase = mock(UpdateRepositoryUseCase.class);
  private CoderadarConfigurationProperties coderadarConfigurationProperties =
      mock(CoderadarConfigurationProperties.class);
  private ProcessProjectService processProjectService = mock(ProcessProjectService.class);

  @Test
  void updateProjectReturnsErrorWhenProjectWithNameStillExists() {
    UpdateProjectService testSubject =
        new UpdateProjectService(
            getProjectPort,
            updateProjectPort,
            updateRepositoryUseCase,
            coderadarConfigurationProperties,
            processProjectService);

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

    Mockito.when(getProjectPort.existsByName(project.getName())).thenReturn(Boolean.TRUE);

    Assertions.assertThrows(
        ProjectAlreadyExistsException.class, () -> testSubject.update(command, 1L));
  }
}
