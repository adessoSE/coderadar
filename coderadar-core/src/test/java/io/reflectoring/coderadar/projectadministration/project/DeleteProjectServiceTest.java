package io.reflectoring.coderadar.projectadministration.project;

import static org.mockito.ArgumentMatchers.anyLong;

import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.DeleteProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.service.ProcessProjectService;
import io.reflectoring.coderadar.projectadministration.service.project.DeleteProjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteProjectServiceTest {

  @Mock private DeleteProjectPort deleteProjectPort;

  @Mock private GetProjectPort getProjectPort;

  @Mock private ProcessProjectService processProjectService;

  @Test
  void deleteProjectWithIdOne() throws ProjectIsBeingProcessedException {
    DeleteProjectService testSubject =
        new DeleteProjectService(deleteProjectPort, processProjectService);

    Mockito.when(getProjectPort.get(anyLong())).thenReturn(new Project());

    testSubject.delete(1L);

    // Mockito.verify(deleteProjectPort, Mockito.times(1)).delete(1L);
  }
}
