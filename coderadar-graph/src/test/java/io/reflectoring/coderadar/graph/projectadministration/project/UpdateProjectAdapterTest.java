package io.reflectoring.coderadar.graph.projectadministration.project;

import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.domain.Project;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.adapter.UpdateProjectAdapter;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Update project")
class UpdateProjectAdapterTest {
  private final ProjectRepository projectRepository = mock(ProjectRepository.class);

  private UpdateProjectAdapter updateProjectAdapter;

  @BeforeEach
  void setUp() {
    updateProjectAdapter = new UpdateProjectAdapter(projectRepository);
  }

  @Test
  @DisplayName("Should update project when a project with the passing ID exists")
  void shouldUpdateProjectWhenAProjectWithThePassingIdExists() {
    ProjectEntity mockedOldItem = new ProjectEntity();
    mockedOldItem.setId(1L);
    mockedOldItem.setName("Mustermann");
    when(projectRepository.findById(anyLong())).thenReturn(Optional.of(mockedOldItem));

    ProjectEntity mockedItem = new ProjectEntity();
    mockedItem.setId(1L);
    mockedItem.setName("Musterfrau");
    when(projectRepository.save(any(ProjectEntity.class))).thenReturn(mockedItem);

    Project project = new Project();
    project.setId(1L);
    project.setName("Musterfrau");
    updateProjectAdapter.update(project);

    verify(projectRepository, times(1)).save(mockedItem);
  }
}
