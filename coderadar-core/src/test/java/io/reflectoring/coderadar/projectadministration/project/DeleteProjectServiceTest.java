package io.reflectoring.coderadar.projectadministration.project;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.DeleteProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.service.project.DeleteProjectService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;

class DeleteProjectServiceTest {
  private DeleteProjectPort deleteProjectPort = mock(DeleteProjectPort.class);
  private GetProjectPort getProjectPort = mock(GetProjectPort.class);

  @Test
  void deleteProjectWithIdOne() {
    DeleteProjectService testSubject = new DeleteProjectService(deleteProjectPort);

    Mockito.when(getProjectPort.get(anyLong())).thenReturn(new Project());

    testSubject.delete(1L);

    Mockito.verify(deleteProjectPort, Mockito.times(1)).delete(1L);
  }
}
