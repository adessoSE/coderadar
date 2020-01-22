package io.reflectoring.coderadar.graph.projectadministration.project;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.adapter.GetProjectAdapter;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Get project")
class GetProjectAdapterTest {
  private ProjectRepository projectRepository = mock(ProjectRepository.class);

  private GetProjectAdapter getProjectAdapter;

  @BeforeEach
  void setUp() {
    getProjectAdapter = new GetProjectAdapter(projectRepository);
  }

  @Test
  @DisplayName("Should return project as optional when a project with the passing ID exists")
  void shouldReturnProjectAsOptionalWhenAProjectWithThePassingIdExists() {
    ProjectEntity mockedItem = new ProjectEntity();
    mockedItem.setId(1L);
    when(projectRepository.findById(any(Long.class))).thenReturn(Optional.of(mockedItem));

    Project returned = getProjectAdapter.get(1L);

    verify(projectRepository, times(1)).findById(1L);
    verifyNoMoreInteractions(projectRepository);
    Assertions.assertNotNull(returned);
    Assertions.assertEquals(new Long(1L), returned.getId());
  }
}
