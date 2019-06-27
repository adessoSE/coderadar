package io.reflectoring.coderadar.graph.query;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.graph.query.repository.GetAvailableMetricsInProjectRepository;
import io.reflectoring.coderadar.graph.query.service.GetAvailableMetricsInProjectAdapter;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Get available metrics in project")
class GetAvailableMetricsInProjectAdapterTest {
  private GetProjectRepository getProjectRepository = mock(GetProjectRepository.class);
  private GetAvailableMetricsInProjectRepository getAvailableMetricsInProjectRepository =
      mock(GetAvailableMetricsInProjectRepository.class);

  private GetAvailableMetricsInProjectAdapter getAvailableMetricsInProjectAdapter;

  @BeforeEach
  void setUp() {
    getAvailableMetricsInProjectAdapter =
        new GetAvailableMetricsInProjectAdapter(
            getProjectRepository, getAvailableMetricsInProjectRepository);
  }

  @Test
  @DisplayName("Should throw exception when a project with the passing ID doesn't exists")
  void shouldThrowExceptionWhenAProjectWithThePassingIdDoesntExists() {
    Throwable thrown = catchThrowable(() -> getAvailableMetricsInProjectAdapter.get(1L));

    assertThat(thrown)
        .isInstanceOf(ProjectNotFoundException.class)
        .hasNoCause()
        .hasMessage("Project with id 1 not found.");
  }

  @Test
  @DisplayName("Should return empty list of strings when no metrics are available")
  void shouldReturnEmptyListOfStringsWhenNoMetricsAreAvailable() {
    Optional<ProjectEntity> mockProject = Optional.of(new ProjectEntity());
    List<String> mockListOfMetrics = new LinkedList<>();
    when(getProjectRepository.findById(anyLong())).thenReturn(mockProject);
    when(getAvailableMetricsInProjectRepository.getAvailableMetricsInProject(anyLong()))
        .thenReturn(mockListOfMetrics);

    List<String> returnedMetrics = getAvailableMetricsInProjectAdapter.get(1L);

    assertThat(returnedMetrics).hasSize(0);
  }

  @Test
  @DisplayName("Should return list of strings with size of one when one metric is available")
  void shouldReturnListOfStringsWithSizeOfOneWhenOneMetricIsAvailable() {
    Optional<ProjectEntity> mockProject = Optional.of(new ProjectEntity());
    List<String> mockListOfMetrics = new LinkedList<>();
    mockListOfMetrics.add("loc");
    when(getProjectRepository.findById(anyLong())).thenReturn(mockProject);
    when(getAvailableMetricsInProjectRepository.getAvailableMetricsInProject(anyLong()))
        .thenReturn(mockListOfMetrics);

    List<String> returnedMetrics = getAvailableMetricsInProjectAdapter.get(1L);

    assertThat(returnedMetrics).hasSize(1).containsExactly("loc");
  }

  @Test
  @DisplayName("Should return list of strings with size of two when two metrics are available")
  void shouldReturnListOfStringsWithSizeOfTwoWhenTwoMetricsAreAvailable() {
    Optional<ProjectEntity> mockProject = Optional.of(new ProjectEntity());
    List<String> mockListOfMetrics = new LinkedList<>();
    mockListOfMetrics.add("sloc");
    mockListOfMetrics.add("cloc");
    when(getProjectRepository.findById(anyLong())).thenReturn(mockProject);
    when(getAvailableMetricsInProjectRepository.getAvailableMetricsInProject(anyLong()))
        .thenReturn(mockListOfMetrics);

    List<String> returnedMetrics = getAvailableMetricsInProjectAdapter.get(1L);

    assertThat(returnedMetrics).hasSize(2).containsExactly("sloc", "cloc");
  }
}
