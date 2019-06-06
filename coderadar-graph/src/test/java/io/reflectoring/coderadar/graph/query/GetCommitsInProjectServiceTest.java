package io.reflectoring.coderadar.graph.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.graph.query.repository.GetCommitsInProjectRepository;
import io.reflectoring.coderadar.graph.query.service.GetCommitsInProjectService;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GetCommitsInProjectServiceTest {
  private GetCommitsInProjectRepository getCommitsInProjectRepository =
      mock(GetCommitsInProjectRepository.class);
  private GetProjectRepository getProjectRepository = mock(GetProjectRepository.class);
  private GetCommitsInProjectService getCommitsInProjectService;

  @Test
  @DisplayName("Should throw exception when a project with the passing ID doesn't exists")
  void shouldThrowExceptionWhenAProjectWithThePassingIdDoesntExists() {
    getCommitsInProjectService =
        new GetCommitsInProjectService(getProjectRepository, getCommitsInProjectRepository);

    Throwable thrown = catchThrowable(() -> getCommitsInProjectService.get(1L));

    assertThat(thrown)
        .isInstanceOf(ProjectNotFoundException.class)
        .hasNoCause()
        .hasMessage("Project with id 1 not found.");
  }
}
