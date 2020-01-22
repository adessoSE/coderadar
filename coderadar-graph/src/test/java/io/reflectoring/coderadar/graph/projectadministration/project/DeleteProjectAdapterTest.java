package io.reflectoring.coderadar.graph.projectadministration.project;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.adapter.DeleteProjectAdapter;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

@DisplayName("Delete project")
class DeleteProjectAdapterTest {
  private ProjectRepository projectRepository = mock(ProjectRepository.class);

  private DeleteProjectAdapter deleteProjectAdapter;

  @BeforeEach
  void setUp() {
    when(projectRepository.findById(anyLong())).thenReturn(Optional.of(new ProjectEntity()));
    deleteProjectAdapter = new DeleteProjectAdapter(projectRepository);
  }

  @Test
  @DisplayName("Should delete project when passing a valid project")
  void shouldDeleteProjectWhenPassingAValidProjectId() {
    when(projectRepository.deleteProjectFindings(anyLong())).thenReturn(0L);
    doNothing().when(projectRepository).deleteProjectFilesAndModules(isA(Long.class));
    doNothing().when(projectRepository).deleteProjectCommits(isA(Long.class));
    when(projectRepository.deleteProjectMetrics(anyLong())).thenReturn(0L);
    doNothing().when(projectRepository).deleteProjectConfiguration(isA(Long.class));

    Project testProject = new Project();
    testProject.setId(1L);
    deleteProjectAdapter.delete(testProject.getId());

    verify(projectRepository, times(1)).deleteProjectFindings(anyLong());
    verify(projectRepository, times(1)).deleteProjectFilesAndModules(anyLong());
    verify(projectRepository, times(1)).deleteProjectCommits(anyLong());
    verify(projectRepository, times(1)).deleteProjectConfiguration(anyLong());
    verify(projectRepository, times(1)).deleteProjectMetrics(anyLong());
  }
}
