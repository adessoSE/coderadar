package io.reflectoring.coderadar.projectadministration.project;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.projectadministration.ProjectAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveCommitPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.CreateProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.projectadministration.service.project.CreateProjectService;
import io.reflectoring.coderadar.vcs.port.driver.GetProjectCommitsUseCase;
import io.reflectoring.coderadar.vcs.port.driver.clone.CloneRepositoryUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.task.TaskExecutor;

import java.io.File;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreateProjectServiceTest {

  private CreateProjectPort createProjectPort = mock(CreateProjectPort.class);
  private GetProjectPort getProjectPort = mock(GetProjectPort.class);
  private CloneRepositoryUseCase cloneRepositoryUseCase = mock(CloneRepositoryUseCase.class);
  private CoderadarConfigurationProperties coderadarConfigurationProperties =
      mock(CoderadarConfigurationProperties.class);
  private TaskExecutor taskExecutor = mock(TaskExecutor.class);

  private GetProjectCommitsUseCase getProjectCommitsUseCase = mock(GetProjectCommitsUseCase.class);
  private SaveCommitPort saveCommitPort = mock(SaveCommitPort.class);

  @Test
  void returnsNewProjectId() {
    CreateProjectService testSubject =
        new CreateProjectService(
            createProjectPort,
            getProjectPort,
            cloneRepositoryUseCase,
            coderadarConfigurationProperties,
            taskExecutor,
            getProjectCommitsUseCase,
            saveCommitPort,
            checkProjectStatusPort);

    when(coderadarConfigurationProperties.getWorkdir())
        .thenReturn(new File("coderadar-workdir").toPath());
    CreateProjectCommand command =
        new CreateProjectCommand(
            "project", "username", "password", "http://valid.url", true, new Date(), new Date());

    Project project = new Project();
    project.setName("project");
    project.setVcsUrl("http://valid.url");
    project.setVcsUsername("username");
    project.setVcsPassword("password");
    project.setVcsOnline(true);
    project.setVcsStart(new Date());
    project.setVcsEnd(new Date());

    when(createProjectPort.createProject(any())).thenReturn(1L);
    when(getProjectPort.existsByName(project.getName())).thenReturn(Boolean.FALSE);

    Long projectId = testSubject.createProject(command);

    Assertions.assertEquals(1L, projectId.longValue());
  }

  @Test
  void returnsErrorWhenProjectWithNameAlreadyExists() {
    CreateProjectService testSubject =
        new CreateProjectService(
            createProjectPort,
            getProjectPort,
            cloneRepositoryUseCase,
            coderadarConfigurationProperties,
            taskExecutor,
            getProjectCommitsUseCase,
            saveCommitPort,
            checkProjectStatusPort);

    when(coderadarConfigurationProperties.getWorkdir())
        .thenReturn(new File("coderadar-workdir").toPath());
    CreateProjectCommand command =
        new CreateProjectCommand(
            "project", "username", "password", "http://valid.url", true, new Date(), new Date());

    Project project = new Project();
    project.setName("project");
    project.setVcsUrl("http://valid.url");
    project.setVcsUsername("username");
    project.setVcsPassword("password");
    project.setVcsOnline(true);
    project.setVcsStart(new Date());
    project.setVcsEnd(new Date());

    when(getProjectPort.existsByName(project.getName())).thenReturn(Boolean.TRUE);

    Assertions.assertThrows(
        ProjectAlreadyExistsException.class,
        () -> {
          testSubject.createProject(command);
        });
  }
}
