package io.reflectoring.coderadar.core.projectadministration.project;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.DeleteProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.core.projectadministration.service.project.DeleteProjectService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DeleteProjectServiceTest {
  private DeleteProjectPort deleteProjectPort = mock(DeleteProjectPort.class);
  private GetProjectPort getProjectPort = mock(GetProjectPort.class);

  @Test
  void deleteProjectWithIdOne() {
    DeleteProjectService testSubject = new DeleteProjectService(deleteProjectPort, getProjectPort);

    Mockito.when(getProjectPort.get(anyLong())).thenReturn(Optional.of(new Project()));

    testSubject.delete(1L);

    Mockito.verify(deleteProjectPort, Mockito.times(1)).delete(1L);
  }
}
