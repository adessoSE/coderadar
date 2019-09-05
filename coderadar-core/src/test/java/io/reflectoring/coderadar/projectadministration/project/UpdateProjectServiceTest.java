package io.reflectoring.coderadar.projectadministration.project;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.projectadministration.ProjectAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.UpdateCommitsPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.ProjectStatusPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.UpdateProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.project.update.UpdateProjectCommand;
import io.reflectoring.coderadar.projectadministration.service.ProcessProjectService;
import io.reflectoring.coderadar.projectadministration.service.project.UpdateProjectService;
import io.reflectoring.coderadar.query.domain.DateRange;
import io.reflectoring.coderadar.vcs.UnableToUpdateRepositoryException;
import io.reflectoring.coderadar.vcs.port.driver.GetProjectCommitsUseCase;
import io.reflectoring.coderadar.vcs.port.driver.UpdateRepositoryUseCase;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.scheduling.TaskScheduler;

@ExtendWith(MockitoExtension.class)
class UpdateProjectServiceTest {

  @Mock private GetProjectPort getProjectPortMock;

  @Mock private UpdateProjectPort updateProjectPortMock;

  @Mock private UpdateRepositoryUseCase updateRepositoryUseCaseMock;

  @Mock private CoderadarConfigurationProperties configurationPropertiesMock;

  @Mock private ProcessProjectService processProjectServiceMock;

  @Mock private GetProjectCommitsUseCase getProjectCommitsUseCaseMock;

  @Mock private UpdateCommitsPort updateCommitsPortMock;

  @Mock private ProjectStatusPort projectStatusPortMock;

  @Mock private TaskScheduler taskSchedulerMock;

  private UpdateProjectService testSubject;

  @BeforeEach
  void setUp() {
    this.testSubject =
        new UpdateProjectService(
            getProjectPortMock,
            updateProjectPortMock,
            updateRepositoryUseCaseMock,
            configurationPropertiesMock,
            processProjectServiceMock,
            getProjectCommitsUseCaseMock,
            updateCommitsPortMock,
            projectStatusPortMock,
            taskSchedulerMock);
  }

  @Test
  void updateProjectReturnsErrorWhenProjectWithNameAlreadyExists(
      @Mock Project projectToUpdateMock) {
    // given
    long projectId = 123L;
    String newProjectName = "new name";
    String newUsername = "newUsername";
    String newPassword = "newPassword";
    Date newStartDate = new Date();
    Date newEndDate = new Date();

    newStartDate.setTime(newStartDate.getTime() + 10000);
    newEndDate.setTime(newEndDate.getTime() + 20000);

    UpdateProjectCommand command =
        new UpdateProjectCommand(
            newProjectName,
            newUsername,
            newPassword,
            "http://valid.url",
            true,
            newStartDate,
            newEndDate);

    Project projectWithCollidingName = new Project().setId(1L).setName(newProjectName);

    when(getProjectPortMock.get(projectId)).thenReturn(projectToUpdateMock);

    when(getProjectPortMock.findByName(newProjectName))
        .thenReturn(Collections.singletonList(projectWithCollidingName));

    // when / then
    assertThatThrownBy(() -> testSubject.update(command, projectId))
        .isInstanceOf(ProjectAlreadyExistsException.class);
  }

  @Test
  void updateProjectSuccessfullyUpdatesProjectIfNameIsUnique(
      @Mock Project projectToUpdateMock, @Mock Commit commitMock)
      throws ProjectIsBeingProcessedException, UnableToUpdateRepositoryException {

    // given
    long projectId = 123L;
    String newProjectName = "new name";
    String newUsername = "newUsername";
    String newPassword = "newPassword";
    String newVcsUrl = "http://new.valid.url";
    Date newStartDate = new Date();
    Date newEndDate = new Date();

    newStartDate.setTime(newStartDate.getTime() + 100000);
    newEndDate.setTime(newEndDate.getTime() + 200000);

    String projectWorkdirName = "project-workdir";
    String globalWorkdirName = "coderadar-workdir";

    DateRange expectedDateRange =
        new DateRange(
            newStartDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
            newEndDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

    UpdateProjectCommand command =
        new UpdateProjectCommand(
            newProjectName, newUsername, newPassword, newVcsUrl, false, newStartDate, newEndDate);

    Path expectedUpdatedRepositoryPath =
        new File(globalWorkdirName + "/projects/" + projectWorkdirName).toPath();

    when(getProjectPortMock.get(projectId)).thenReturn(projectToUpdateMock);
    when(projectToUpdateMock.getWorkdirName()).thenReturn(projectWorkdirName);
    when(projectToUpdateMock.getVcsStart()).thenReturn(new Date());
    when(projectToUpdateMock.getVcsEnd()).thenReturn(new Date());

    when(getProjectPortMock.findByName(newProjectName)).thenReturn(Collections.emptyList());

    doAnswer(
            (Answer<Void>)
                invocation -> {
                  Runnable runnable = invocation.getArgument(0);
                  runnable.run();

                  return null;
                })
        .when(processProjectServiceMock)
        .executeTask(any(), eq(projectId));

    when(configurationPropertiesMock.getWorkdir()).thenReturn(new File(globalWorkdirName).toPath());

    when(getProjectCommitsUseCaseMock.getCommits(Paths.get(projectWorkdirName), expectedDateRange))
        .thenReturn(Collections.singletonList(commitMock));

    // when
    testSubject.update(command, projectId);

    // then
    verify(projectToUpdateMock, never()).setId(anyLong());
    verify(projectToUpdateMock).setName(newProjectName);
    verify(projectToUpdateMock).setVcsUrl(newVcsUrl);
    verify(projectToUpdateMock).setVcsUsername(newUsername);
    verify(projectToUpdateMock).setVcsPassword(newPassword);
    verify(projectToUpdateMock).setVcsStart(newStartDate);
    verify(projectToUpdateMock).setVcsEnd(newEndDate);
    verify(projectToUpdateMock).setVcsOnline(false);

    verify(updateRepositoryUseCaseMock).updateRepository(expectedUpdatedRepositoryPath);
    verify(updateCommitsPortMock).updateCommits(Collections.singletonList(commitMock), projectId);
  }
}
