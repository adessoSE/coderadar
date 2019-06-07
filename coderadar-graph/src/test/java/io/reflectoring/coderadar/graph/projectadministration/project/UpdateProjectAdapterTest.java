package io.reflectoring.coderadar.graph.projectadministration.project;

import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.UpdateProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.service.UpdateProjectAdapter;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Update project")
class UpdateProjectAdapterTest {
  private GetProjectRepository getProjectRepository = mock(GetProjectRepository.class);

  private UpdateProjectRepository updateProjectRepository = mock(UpdateProjectRepository.class);

  private UpdateProjectAdapter updateProjectAdapter;

  @BeforeEach
  void setUp() {
    updateProjectAdapter = new UpdateProjectAdapter(getProjectRepository, updateProjectRepository);
  }

  @Test
  @DisplayName("Should throw exception when a project with the passing ID doesn't exists")
  void shouldThrowExceptionWhenAProjectWithThePassingIdDoesntExists() {
    Assertions.assertThrows(
        ProjectNotFoundException.class, () -> updateProjectAdapter.update(new Project()));
  }

  @Test
  @DisplayName("Should update project when a project with the passing ID exists")
  void shouldUpdateProjectWhenAProjectWithThePassingIdExists() {
    Project mockedOldItem = new Project();
    mockedOldItem.setId(1L);
    mockedOldItem.setName("Mustermann");
    when(getProjectRepository.findById(any(Long.class))).thenReturn(Optional.of(mockedOldItem));

    Project mockedItem = new Project();
    mockedItem.setId(1L);
    mockedItem.setName("Musterfrau");
    when(updateProjectRepository.save(any(Project.class))).thenReturn(mockedItem);

    Project project = new Project();
    project.setId(1L);
    project.setName("Musterfrau");
    updateProjectAdapter.update(project);

    verify(updateProjectRepository, times(1)).save(mockedItem);
    Assertions.assertNotEquals(mockedOldItem, project);
  }
}
