package io.reflectoring.coderadar.graph.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.graph.exception.ProjectNotFoundException;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.graph.query.repository.GetAvailableMetricsInProjectRepository;
import io.reflectoring.coderadar.graph.query.service.GetAvailableMetricsInProjectService;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName("Get available metrics in project")
class GetAvailableMetricsInProjectServiceTest {
  @Mock private GetProjectRepository getProjectRepository;
  @Mock private GetAvailableMetricsInProjectRepository getAvailableMetricsInProjectRepository;

  @InjectMocks private GetAvailableMetricsInProjectService getAvailableMetricsInProjectService;

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
    when(getAvailableMetricsInProjectRepository.findMetricsInProject(anyLong()))
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
    when(getAvailableMetricsInProjectRepository.findMetricsInProject(anyLong()))
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
    when(getAvailableMetricsInProjectRepository.findMetricsInProject(anyLong()))
        .thenReturn(mockListOfMetrics);

    List<String> returnedMetrics = getAvailableMetricsInProjectService.get(1L);

    assertThat(returnedMetrics).hasSize(2).containsExactly("sloc", "cloc");
  }
}
