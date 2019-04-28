package io.reflectoring.coderadar.graph.projectadministration.project;

import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.DeleteProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.service.DeleteProjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DeleteProjectServiceTest {
  @Mock private DeleteProjectRepository deleteProjectRepository;

  @InjectMocks private DeleteProjectService deleteProjectService;

  @Test
  public void withProjectEntityShouldCallDeleteProjectMethodOfRepository() {
    doNothing().when(deleteProjectRepository).delete(isA(Project.class));
    deleteProjectService.delete(new Project());
    verify(deleteProjectRepository, times(1)).delete(any(Project.class));
  }

  @Test
  public void withProjectIdShouldCallDeleteProjectMethodOfRepository() {
    doNothing().when(deleteProjectRepository).deleteById(isA(Long.class));
    deleteProjectService.delete(1L);
    verify(deleteProjectRepository, times(1)).deleteById(any(Long.class));
  }
}
