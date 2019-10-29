package io.reflectoring.coderadar.projectadministration.project;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.projectadministration.ProjectAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveCommitPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.UpdateProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.module.create.CreateModuleUseCase;
import io.reflectoring.coderadar.projectadministration.port.driver.module.get.ListModulesOfProjectUseCase;
import io.reflectoring.coderadar.projectadministration.port.driver.project.update.UpdateProjectCommand;
import io.reflectoring.coderadar.projectadministration.service.ProcessProjectService;
import io.reflectoring.coderadar.projectadministration.service.project.UpdateProjectService;
import io.reflectoring.coderadar.query.domain.DateRange;
import io.reflectoring.coderadar.vcs.UnableToUpdateRepositoryException;
import io.reflectoring.coderadar.vcs.port.driver.GetProjectCommitsUseCase;
import io.reflectoring.coderadar.vcs.port.driver.UpdateRepositoryUseCase;
import java.io.File;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

@ExtendWith(MockitoExtension.class)
class UpdateProjectServiceTest {

  @Mock private GetProjectPort getProjectPortMock;

  @Mock private UpdateProjectPort updateProjectPortMock;

  @Mock private UpdateRepositoryUseCase updateRepositoryUseCaseMock;

  @Mock private CoderadarConfigurationProperties configurationPropertiesMock;

  @Mock private ProcessProjectService processProjectServiceMock;

  @Mock private GetProjectCommitsUseCase getProjectCommitsUseCaseMock;

  @Mock private SaveCommitPort saveCommitPortMock;

  @Mock private CreateModuleUseCase createModuleUseCase;

  @Mock private ListModulesOfProjectUseCase listModulesOfProjectUseCase;

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
            saveCommitPortMock,
            listModulesOfProjectUseCase,
            createModuleUseCase);
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
  void updateProjectSuccessfullyUpdatesProjectIfNameIsUnique(@Mock Commit commitMock)
      throws ProjectIsBeingProcessedException, UnableToUpdateRepositoryException {

    // given
    long projectId = 123L;
    String newProjectName = "new name";
    String newUsername = "newUsername";
    String newPassword = "newPassword";
    String newVcsUrl = "https://valid.com";
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

    Project testProject = new Project();
    testProject.setWorkdirName(projectWorkdirName);
    testProject.setVcsStart(new Date());
    testProject.setVcsEnd(new Date());
    testProject.setVcsUrl("");
    when(getProjectPortMock.get(projectId)).thenReturn(testProject);

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
    Assert.assertEquals(testProject.getName(), newProjectName);
    Assert.assertEquals(testProject.getVcsUsername(), newUsername);
    Assert.assertEquals(testProject.getVcsPassword(), newPassword);
    Assert.assertEquals(testProject.getVcsStart(), newStartDate);
    Assert.assertEquals(testProject.getVcsEnd(), newEndDate);
    Assert.assertEquals(testProject.getVcsUrl(), newVcsUrl);

    verify(updateRepositoryUseCaseMock).updateRepository(any(), any());
    verify(saveCommitPortMock).saveCommits(Collections.singletonList(commitMock), projectId);
  }
}
