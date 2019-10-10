package io.reflectoring.coderadar.graph.projectadministration.project;

import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.service.DeleteProjectAdapter;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Delete project")
class DeleteProjectAdapterTest {
  private ProjectRepository projectRepository = mock(ProjectRepository.class);
  private CommitRepository commitRepository = mock(CommitRepository.class);
  private CoderadarConfigurationProperties coderadarConfigurationProperties =
      mock(CoderadarConfigurationProperties.class);

  private DeleteProjectAdapter deleteProjectAdapter;

  @BeforeEach
  void setUp() {
    when(projectRepository.findById(anyLong())).thenReturn(Optional.of(new ProjectEntity()));
    deleteProjectAdapter =
        new DeleteProjectAdapter(
            projectRepository, coderadarConfigurationProperties, commitRepository);
  }

  @Test
  @DisplayName("Should delete project when passing a valid project entity")
  void shouldDeleteProjectWhenPassingAValidProjectEntity() {
    doNothing().when(projectRepository).deleteProjectCascade(isA(Long.class));

    Project testProject = new Project();
    testProject.setId(1L);
    deleteProjectAdapter.delete(testProject.getId());

    verify(projectRepository, times(1)).deleteProjectCascade(anyLong());
  }

  @Test
  @DisplayName("Should delete project when passing a valid project id")
  void shouldDeleteProjectWhenPassingAValidProjectId() {
    doNothing().when(projectRepository).deleteProjectCascade(isA(Long.class));

    deleteProjectAdapter.delete(1L);

    verify(projectRepository, times(1)).deleteProjectCascade(any(Long.class));
  }
}
