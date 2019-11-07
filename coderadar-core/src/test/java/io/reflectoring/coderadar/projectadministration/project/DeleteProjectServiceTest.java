package io.reflectoring.coderadar.projectadministration.project;

import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.DeleteProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.service.ProcessProjectService;
import io.reflectoring.coderadar.projectadministration.service.project.DeleteProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteProjectServiceTest {

  @Mock private DeleteProjectPort deleteProjectPortMock;

  @Mock private ProcessProjectService processProjectServiceMock;

  @Mock private GetProjectPort getProjectPort;

  private DeleteProjectService testSubject;

  @BeforeEach
  void setUp() {
    this.testSubject =
        new DeleteProjectService(deleteProjectPortMock, processProjectServiceMock, getProjectPort);
    when(getProjectPort.get(anyLong())).thenReturn(new Project());
  }

  @Test
  void deleteProjectWithIdOne() throws ProjectIsBeingProcessedException {
    // given
    long projectId = 1L;

    doAnswer(
            (Answer<Void>)
                invocation -> {
                  Runnable runnable = invocation.getArgument(0);
                  runnable.run();

                  return null;
                })
        .when(processProjectServiceMock)
        .executeTask(any(), anyLong());

    // when
    testSubject.delete(projectId);

    // then
    verify(deleteProjectPortMock).delete(projectId);
  }
}
