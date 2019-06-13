package io.reflectoring.coderadar.graph.projectadministration.project;

import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.graph.projectadministration.project.repository.DeleteProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.service.DeleteProjectAdapter;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Delete project")
class DeleteProjectAdapterTest {
  private DeleteProjectRepository deleteProjectRepository = mock(DeleteProjectRepository.class);

  private DeleteProjectAdapter deleteProjectAdapter;

  @BeforeEach
  void setUp() {
    when(deleteProjectRepository.findById(anyLong())).thenReturn(Optional.of(new Project()));
    deleteProjectAdapter = new DeleteProjectAdapter(deleteProjectRepository);
  }

  @Test
  @DisplayName("Should delete project when passing a valid project entity")
  void shouldDeleteProjectWhenPassingAValidProjectEntity() {
    doNothing().when(deleteProjectRepository).deleteProjectCascade(isA(Long.class));

    Project testProject = new Project();
    testProject.setId(1L);
    deleteProjectAdapter.delete(testProject);

    verify(deleteProjectRepository, times(1)).deleteProjectCascade(anyLong());
  }

  @Test
  @DisplayName("Should delete project when passing a valid project id")
  void shouldDeleteProjectWhenPassingAValidProjectId() {
    doNothing().when(deleteProjectRepository).deleteProjectCascade(isA(Long.class));

    deleteProjectAdapter.delete(1L);

    verify(deleteProjectRepository, times(1)).deleteProjectCascade(any(Long.class));
  }
}
