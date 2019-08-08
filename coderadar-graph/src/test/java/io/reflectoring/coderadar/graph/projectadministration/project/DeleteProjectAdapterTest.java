package io.reflectoring.coderadar.graph.projectadministration.project;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.DeleteProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.service.DeleteProjectAdapter;
import io.reflectoring.coderadar.graph.query.repository.DeleteCommitsRepository;
import io.reflectoring.coderadar.graph.query.repository.GetCommitsInProjectRepository;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

@DisplayName("Delete project")
class DeleteProjectAdapterTest {
  private DeleteProjectRepository deleteProjectRepository = mock(DeleteProjectRepository.class);
  private GetCommitsInProjectRepository getCommitsInProjectRepository =
      mock(GetCommitsInProjectRepository.class);
  private DeleteCommitsRepository deleteCommitsRepository = mock(DeleteCommitsRepository.class);
  private CoderadarConfigurationProperties coderadarConfigurationProperties =
      mock(CoderadarConfigurationProperties.class);

  private DeleteProjectAdapter deleteProjectAdapter;

  @BeforeEach
  void setUp() {
    when(deleteProjectRepository.findById(anyLong())).thenReturn(Optional.of(new ProjectEntity()));
    deleteProjectAdapter =
        new DeleteProjectAdapter(
            deleteProjectRepository,
            coderadarConfigurationProperties,
            getCommitsInProjectRepository,
            deleteCommitsRepository);
  }

  @Test
  @DisplayName("Should delete project when passing a valid project entity")
  void shouldDeleteProjectWhenPassingAValidProjectEntity() {
    doNothing().when(deleteProjectRepository).deleteProjectCascade(isA(Long.class));

    Project testProject = new Project();
    testProject.setId(1L);
    deleteProjectAdapter.delete(testProject.getId());

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
