package io.reflectoring.coderadar.graph.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.graph.query.adapter.GetAvailableMetricsInProjectAdapter;
import io.reflectoring.coderadar.graph.query.repository.MetricQueryRepository;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Get available metrics in project")
class GetAvailableMetricsInProjectAdapterTest {
  private final ProjectRepository projectRepository = mock(ProjectRepository.class);
  private final MetricQueryRepository metricQueryRepository = mock(MetricQueryRepository.class);

  private GetAvailableMetricsInProjectAdapter getAvailableMetricsInProjectAdapter;

  @BeforeEach
  void setUp() {
    getAvailableMetricsInProjectAdapter =
        new GetAvailableMetricsInProjectAdapter(metricQueryRepository);
  }

  @Test
  @DisplayName("Should return empty list of strings when no metrics are available")
  void shouldReturnEmptyListOfStringsWhenNoMetricsAreAvailable() {
    List<Integer> mockListOfMetrics = new LinkedList<>();
    when(projectRepository.existsById(anyLong())).thenReturn(true);
    when(metricQueryRepository.getAvailableMetricsInProject(anyLong()))
        .thenReturn(mockListOfMetrics);

    List<String> returnedMetrics = getAvailableMetricsInProjectAdapter.get(1L);

    assertThat(returnedMetrics).isEmpty();
  }

  @Test
  @DisplayName("Should return list of strings with size of one when one metric is available")
  void shouldReturnListOfStringsWithSizeOfOneWhenOneMetricIsAvailable() {
    List<Integer> mockListOfMetrics = new LinkedList<>();
    mockListOfMetrics.add(1);
    when(projectRepository.existsById(anyLong())).thenReturn(true);
    when(metricQueryRepository.getAvailableMetricsInProject(anyLong()))
        .thenReturn(mockListOfMetrics);

    List<String> returnedMetrics = getAvailableMetricsInProjectAdapter.get(1L);

    assertThat(returnedMetrics).hasSize(1).containsExactly("coderadar:size:sloc:java");
  }

  @Test
  @DisplayName("Should return list of strings with size of two when two metrics are available")
  void shouldReturnListOfStringsWithSizeOfTwoWhenTwoMetricsAreAvailable() {
    List<Integer> mockListOfMetrics = new LinkedList<>();
    mockListOfMetrics.add(1);
    mockListOfMetrics.add(2);
    when(projectRepository.existsById(anyLong())).thenReturn(true);
    when(metricQueryRepository.getAvailableMetricsInProject(anyLong()))
        .thenReturn(mockListOfMetrics);

    List<String> returnedMetrics = getAvailableMetricsInProjectAdapter.get(1L);

    assertThat(returnedMetrics)
        .hasSize(2)
        .containsExactly("coderadar:size:sloc:java", "coderadar:size:cloc:java");
  }
}
