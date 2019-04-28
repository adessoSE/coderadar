package io.reflectoring.coderadar.graph.projectadministration.project;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.service.GetProjectService;
import java.util.Optional;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GetProjectServiceTest {
  @Mock private GetProjectRepository getProjectRepository;

  @InjectMocks private GetProjectService getProjectService;

  @Test
  public void withProjectIdShouldReturnProjectEntityAsOptional() {
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
