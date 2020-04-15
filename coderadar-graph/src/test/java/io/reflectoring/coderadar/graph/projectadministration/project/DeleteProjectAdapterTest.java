package io.reflectoring.coderadar.graph.projectadministration.project;

import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.graph.contributor.repository.ContributorRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.adapter.DeleteProjectAdapter;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Delete project")
class DeleteProjectAdapterTest {
  private ProjectRepository projectRepository = mock(ProjectRepository.class);
  private ContributorRepository contributorRepository = mock(ContributorRepository.class);

  private DeleteProjectAdapter deleteProjectAdapter;

  @BeforeEach
  void setUp() {
    when(projectRepository.findById(anyLong())).thenReturn(Optional.of(new ProjectEntity()));
    deleteProjectAdapter = new DeleteProjectAdapter(projectRepository, contributorRepository);
  }

  @Test
  @DisplayName("Should delete project when passing a valid project")
  void shouldDeleteProjectWhenPassingAValidProjectId() {
    when(projectRepository.deleteProjectFilesAndModules(anyLong())).thenReturn(0L);
    doNothing().when(projectRepository).deleteProjectCommits(isA(Long.class));
    when(projectRepository.deleteProjectMetrics(anyLong())).thenReturn(0L);
    doNothing().when(projectRepository).deleteProjectConfiguration(isA(Long.class));

    Project testProject = new Project();
    testProject.setId(1L);
    deleteProjectAdapter.delete(testProject.getId());

    verify(projectRepository, times(1)).deleteProjectFilesAndModules(anyLong());
    verify(projectRepository, times(1)).deleteProjectCommits(anyLong());
    verify(projectRepository, times(1)).deleteProjectConfiguration(anyLong());
    verify(projectRepository, times(1)).deleteProjectMetrics(anyLong());
    verify(projectRepository, times(1)).deleteContributorRelationships(anyLong());
    verify(contributorRepository, times(1)).deleteContributorsWithoutProjects();
  }
}
