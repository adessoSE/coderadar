package io.reflectoring.coderadar.core.projectadministration.project;

import static org.mockito.ArgumentMatchers.anyLong;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.DeleteProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.core.projectadministration.service.project.DeleteProjectService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class DeleteProjectServiceTest {
  @Mock private DeleteProjectPort deleteProjectPort;
  @Mock private GetProjectPort getProjectPort;

  @InjectMocks private DeleteProjectService testSubject;

  @Test
  void deleteProjectWithIdOne() {
    Mockito.when(getProjectPort.get(anyLong())).thenReturn(Optional.of(new Project()));

    testSubject.delete(1L);

    Mockito.verify(deleteProjectPort, Mockito.times(1)).delete(1L);
  }
}
