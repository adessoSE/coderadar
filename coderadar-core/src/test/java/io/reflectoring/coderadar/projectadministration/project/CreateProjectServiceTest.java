package io.reflectoring.coderadar.projectadministration.project;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.projectadministration.ProjectAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveCommitPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.CreateProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.project.create.CreateProjectCommand;
import io.reflectoring.coderadar.projectadministration.service.ProcessProjectService;
import io.reflectoring.coderadar.projectadministration.service.project.CreateProjectService;
import io.reflectoring.coderadar.projectadministration.service.project.ScanProjectScheduler;
import io.reflectoring.coderadar.query.domain.DateRange;
import io.reflectoring.coderadar.vcs.UnableToCloneRepositoryException;
import io.reflectoring.coderadar.vcs.port.driver.GetProjectCommitsUseCase;
import io.reflectoring.coderadar.vcs.port.driver.clone.CloneRepositoryCommand;
import io.reflectoring.coderadar.vcs.port.driver.clone.CloneRepositoryUseCase;
import java.io.File;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

@ExtendWith(MockitoExtension.class)
class CreateProjectServiceTest {

  @Mock private CreateProjectPort createProjectPort;

  @Mock private GetProjectPort getProjectPort;

  @Mock private CloneRepositoryUseCase cloneRepositoryUseCase;

  @Mock private CoderadarConfigurationProperties coderadarConfigurationProperties;

  @Mock private GetProjectCommitsUseCase getProjectCommitsUseCase;

  @Mock private SaveCommitPort saveCommitPort;

  @Mock private ProcessProjectService processProjectService;

  @Mock private ScanProjectScheduler scanProjectScheduler;

  @Mock private CreateProjectService.WorkdirNameGenerator workdirNameGeneratorMock;

  private CreateProjectService testSubject;

  @BeforeEach
  void setUp() {
    this.testSubject =
        new CreateProjectService(
            createProjectPort,
            getProjectPort,
            cloneRepositoryUseCase,
            coderadarConfigurationProperties,
            processProjectService,
            getProjectCommitsUseCase,
            saveCommitPort,
            scanProjectScheduler,
            workdirNameGeneratorMock);
  }

  @Test
  void returnsNewProjectIdAndClonesRepositoryAndSavesCommits(@Mock Commit commitMock)
      throws ProjectIsBeingProcessedException, UnableToCloneRepositoryException {
    // given
    long expectedProjectId = 1L;
    String name = "proj";
    String vcsUsername = "user";
    String vcsPassword = "pass";
    String vcsUrl = "http://valid.url";
    Date startDate = new Date();
    Date endDate = new Date();
    String projectWorkdirName = "project-workdir";
    String globalWorkdirName = "coderadar-workdir";
    DateRange expectedDateRange =
        new DateRange(
            startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
            endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    List<Commit> expectedCommits = Collections.singletonList(commitMock);

    CreateProjectCommand createCommand =
        new CreateProjectCommand(name, vcsUsername, vcsPassword, vcsUrl, false, startDate, endDate);

    Project expectedProject =
        new Project()
            .setName(name)
            .setVcsUrl(vcsUrl)
            .setVcsUsername(vcsUsername)
            .setVcsPassword(vcsPassword)
            .setVcsOnline(false)
            .setVcsStart(startDate)
            .setVcsEnd(endDate)
            .setBeingProcessed(false)
            .setWorkdirName(projectWorkdirName);

    CloneRepositoryCommand expectedCloneCommand =
        new CloneRepositoryCommand(
            vcsUrl, new File(globalWorkdirName + "/projects/" + projectWorkdirName));

    when(workdirNameGeneratorMock.generate(name)).thenReturn(projectWorkdirName);
    when(createProjectPort.createProject(expectedProject))
        .thenAnswer(
            (Answer<Long>)
                invocation -> {
                  Project passedProject = invocation.getArgument(0);
                  passedProject.setId(expectedProjectId);

                  return expectedProjectId;
                });

    doAnswer(
            (Answer<Void>)
                invocation -> {
                  Runnable runnable = invocation.getArgument(0);
                  runnable.run();

                  return null;
                })
        .when(processProjectService)
        .executeTask(any(), eq(expectedProjectId));

    when(coderadarConfigurationProperties.getWorkdir())
        .thenReturn(new File(globalWorkdirName).toPath());

    when(getProjectCommitsUseCase.getCommits(Paths.get(projectWorkdirName), expectedDateRange))
        .thenReturn(expectedCommits);

    // when
    long actualProjectId = testSubject.createProject(createCommand);

    // then
    assertThat(actualProjectId).isEqualTo(expectedProjectId);

    verify(cloneRepositoryUseCase).cloneRepository(expectedCloneCommand);
    verify(saveCommitPort).saveCommits(expectedCommits, expectedProjectId);
  }

  @Test
  void returnsErrorWhenProjectWithNameAlreadyExists() {
    // given
    String projectName = "a new project";

    CreateProjectCommand command =
        new CreateProjectCommand(
            projectName, "username", "password", "http://valid.url", true, new Date(), new Date());

    when(getProjectPort.existsByName(projectName)).thenReturn(true);

    // when / then
    assertThatThrownBy(() -> testSubject.createProject(command))
        .isInstanceOf(ProjectAlreadyExistsException.class);
  }
}
