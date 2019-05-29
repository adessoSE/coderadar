package io.reflectoring.coderadar.graph.query;

import io.reflectoring.coderadar.core.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.graph.query.repository.GetAvailableMetricsInProjectRepository;
import io.reflectoring.coderadar.graph.query.service.GetAvailableMetricsInProjectService;
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
class GetAvailableMetricsInProjectServiceTest {
  private GetProjectRepository getProjectRepository = mock(GetProjectRepository.class);
  private GetAvailableMetricsInProjectRepository getAvailableMetricsInProjectRepository =
      mock(GetAvailableMetricsInProjectRepository.class);

  private GetAvailableMetricsInProjectService getAvailableMetricsInProjectService;

  @BeforeEach
  void setUp() {
    getAvailableMetricsInProjectService =
        new GetAvailableMetricsInProjectService(
            getProjectRepository, getAvailableMetricsInProjectRepository);
  }

  @Test
  @DisplayName("Should throw exception when a project with the passing ID doesn't exists")
  void shouldThrowExceptionWhenAProjectWithThePassingIdDoesntExists() {
    Throwable thrown = catchThrowable(() -> getAvailableMetricsInProjectService.get(1L));

    assertThat(thrown)
        .isInstanceOf(ProjectNotFoundException.class)
        .hasNoCause()
        .hasMessage("A project with the ID '1' doesn't exists.");
  }

  @Test
  @DisplayName("Should return empty list of strings when no metrics are available")
  void shouldReturnEmptyListOfStringsWhenNoMetricsAreAvailable() {
    Optional<Project> mockProject = Optional.of(new Project());
    List<String> mockListOfMetrics = new LinkedList<>();
    when(getProjectRepository.findById(anyLong())).thenReturn(mockProject);
    when(getAvailableMetricsInProjectRepository.getAvailableMetricsInProject(anyLong()))
        .thenReturn(mockListOfMetrics);

    List<String> returnedMetrics = getAvailableMetricsInProjectService.get(1L);

    assertThat(returnedMetrics).hasSize(0);
  }

  @Test
  @DisplayName("Should return list of strings with size of one when one metric is available")
  void shouldReturnListOfStringsWithSizeOfOneWhenOneMetricIsAvailable() {
    Optional<Project> mockProject = Optional.of(new Project());
    List<String> mockListOfMetrics = new LinkedList<>();
    mockListOfMetrics.add("loc");
    when(getProjectRepository.findById(anyLong())).thenReturn(mockProject);
    when(getAvailableMetricsInProjectRepository.getAvailableMetricsInProject(anyLong()))
        .thenReturn(mockListOfMetrics);

    List<String> returnedMetrics = getAvailableMetricsInProjectService.get(1L);

    assertThat(returnedMetrics).hasSize(1).containsExactly("loc");
  }

  @Test
  @DisplayName("Should return list of strings with size of two when two metrics are available")
  void shouldReturnListOfStringsWithSizeOfTwoWhenTwoMetricsAreAvailable() {
    Optional<Project> mockProject = Optional.of(new Project());
    List<String> mockListOfMetrics = new LinkedList<>();
    mockListOfMetrics.add("sloc");
    mockListOfMetrics.add("cloc");
    when(getProjectRepository.findById(anyLong())).thenReturn(mockProject);
    when(getAvailableMetricsInProjectRepository.getAvailableMetricsInProject(anyLong()))
        .thenReturn(mockListOfMetrics);

    List<String> returnedMetrics = getAvailableMetricsInProjectService.get(1L);

    assertThat(returnedMetrics).hasSize(2).containsExactly("sloc", "cloc");
  }
}
