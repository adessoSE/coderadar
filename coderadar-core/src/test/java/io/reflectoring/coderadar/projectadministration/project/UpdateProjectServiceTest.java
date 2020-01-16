package io.reflectoring.coderadar.projectadministration.project;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.analyzer.port.driven.ResetAnalysisPort;
import io.reflectoring.coderadar.projectadministration.ProjectAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveCommitPort;
import io.reflectoring.coderadar.projectadministration.port.driven.module.CreateModulePort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.UpdateProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.module.get.ListModulesOfProjectUseCase;
import io.reflectoring.coderadar.projectadministration.port.driver.project.update.UpdateProjectCommand;
import io.reflectoring.coderadar.projectadministration.service.ProcessProjectService;
import io.reflectoring.coderadar.projectadministration.service.project.UpdateProjectService;
import io.reflectoring.coderadar.query.domain.DateRange;
import io.reflectoring.coderadar.vcs.UnableToUpdateRepositoryException;
import io.reflectoring.coderadar.vcs.port.driver.ExtractProjectCommitsUseCase;
import io.reflectoring.coderadar.vcs.port.driver.update.UpdateRepositoryUseCase;
import java.io.File;
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

  @Mock private ExtractProjectCommitsUseCase extractProjectCommitsUseCaseMock;

  @Mock private SaveCommitPort saveCommitPortMock;

  @Mock private CreateModulePort createModulePort;

  @Mock private ListModulesOfProjectUseCase listModulesOfProjectUseCase;

  @Mock private ResetAnalysisPort resetAnalysisPort;

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
            extractProjectCommitsUseCaseMock,
            saveCommitPortMock,
            listModulesOfProjectUseCase,
            resetAnalysisPort,
            createModulePort);
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

    Project projectWithCollidingName = new Project().setId(2L).setName(newProjectName);

    when(getProjectPortMock.get(projectId)).thenReturn(projectToUpdateMock);

    when(getProjectPortMock.existsByName(newProjectName)).thenReturn(true);
    when(getProjectPortMock.get(newProjectName)).thenReturn(projectWithCollidingName);

    // when / then
    assertThatThrownBy(() -> testSubject.update(command, projectId))
        .isInstanceOf(ProjectAlreadyExistsException.class);
  }

  @Test
  void updateProjectSuccessfullyUpdatesProjectIfNameIsUnique(@Mock Commit commitMock)
      throws ProjectIsBeingProcessedException, UnableToUpdateRepositoryException {

    // given
    long projectId = 1L;
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
    testProject.setId(1L);
    testProject.setWorkdirName(projectWorkdirName);
    testProject.setVcsStart(new Date());
    testProject.setVcsEnd(new Date());
    testProject.setVcsUrl("");

    when(getProjectPortMock.get(projectId)).thenReturn(testProject);
    when(getProjectPortMock.existsByName(newProjectName)).thenReturn(true);
    when(getProjectPortMock.get(newProjectName)).thenReturn(testProject);

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

    when(extractProjectCommitsUseCaseMock.getCommits(any(), any()))
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

    verify(updateRepositoryUseCaseMock).updateRepository(any());
    verify(saveCommitPortMock).saveCommits(Collections.singletonList(commitMock), projectId);
  }
}
