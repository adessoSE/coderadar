package io.reflectoring.coderadar.graph.query;

import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.graph.query.repository.GetCommitsInProjectRepository;
import io.reflectoring.coderadar.graph.query.service.GetCommitsInProjectAdapter;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.Mockito.mock;

class GetCommitsInProjectAdapterTest {
  private GetCommitsInProjectRepository getCommitsInProjectRepository =
      mock(GetCommitsInProjectRepository.class);
  private GetProjectRepository getProjectRepository = mock(GetProjectRepository.class);
  private GetCommitsInProjectAdapter getCommitsInProjectAdapter;

  @Test
  @DisplayName("Should throw exception when a project with the passing ID doesn't exists")
  void shouldThrowExceptionWhenAProjectWithThePassingIdDoesntExists() {
    getCommitsInProjectAdapter =
        new GetCommitsInProjectAdapter(getProjectRepository, getCommitsInProjectRepository);

    Throwable thrown = catchThrowable(() -> getCommitsInProjectAdapter.get(1L));

    assertThat(thrown)
        .isInstanceOf(ProjectNotFoundException.class)
        .hasNoCause()
        .hasMessage("Project with id 1 not found.");
  }
}
