package io.reflectoring.coderadar.graph.projectadministration.project;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.service.GetProjectService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Get project")
class GetProjectServiceTest {
  @Mock private GetProjectRepository getProjectRepository;

  @InjectMocks private GetProjectService getProjectService;

  @Test
  @DisplayName("Should return project as optional when a project with the passing ID exists")
  void shouldReturnProjectAsOptionalWhenAProjectWithThePassingIdExists() {
    Project mockedItem = new Project();
    mockedItem.setId(1L);
    when(getProjectRepository.findById(any(Long.class))).thenReturn(Optional.of(mockedItem));

    Optional<Project> returned = getProjectService.get(1L);

    verify(getProjectRepository, times(1)).findById(1L);
    verifyNoMoreInteractions(getProjectRepository);
    Assertions.assertTrue(returned.isPresent());
    Assertions.assertEquals(new Long(1L), returned.get().getId());
  }
}
