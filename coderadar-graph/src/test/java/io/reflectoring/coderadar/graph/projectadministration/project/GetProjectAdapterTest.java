package io.reflectoring.coderadar.graph.projectadministration.project;

import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.domain.Project;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.adapter.GetProjectAdapter;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Get project")
class GetProjectAdapterTest {
  private ProjectRepository projectRepository = mock(ProjectRepository.class);
  private UserRepository userRepository = mock(UserRepository.class);

  private GetProjectAdapter getProjectAdapter;

  @BeforeEach
  void setUp() {
    getProjectAdapter = new GetProjectAdapter(projectRepository, userRepository);
  }

  @Test
  @DisplayName("Should return project as optional when a project with the passing ID exists")
  void shouldReturnProjectAsOptionalWhenAProjectWithThePassingIdExists() {
    ProjectEntity mockedItem = new ProjectEntity();
    mockedItem.setId(1L);
    when(projectRepository.findById(anyLong())).thenReturn(Optional.of(mockedItem));

    Project returned = getProjectAdapter.get(1L);

    verify(projectRepository, times(1)).findById(1L);
    verifyNoMoreInteractions(projectRepository);
    Assertions.assertNotNull(returned);
    Assertions.assertEquals(1L, returned.getId());
  }
}
