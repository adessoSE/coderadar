package io.reflectoring.coderadar.graph.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.graph.analyzer.repository.CommitRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.graph.query.service.GetCommitsInProjectAdapter;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GetCommitsInProjectAdapterTest {
  private CommitRepository commitRepository = mock(CommitRepository.class);
  private ProjectRepository projectRepository = mock(ProjectRepository.class);
  private GetCommitsInProjectAdapter getCommitsInProjectAdapter;

  @Test
  @DisplayName("Should throw exception when a project with the passing ID doesn't exists")
  void shouldThrowExceptionWhenAProjectWithThePassingIdDoesntExists() {
    getCommitsInProjectAdapter =
        new GetCommitsInProjectAdapter(projectRepository, commitRepository);

    Throwable thrown =
        catchThrowable(() -> getCommitsInProjectAdapter.getSortedByTimestampDesc(1L));

    assertThat(thrown)
        .isInstanceOf(ProjectNotFoundException.class)
        .hasNoCause()
        .hasMessage("Project with id 1 not found.");
  }
}
