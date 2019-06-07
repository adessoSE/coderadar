package io.reflectoring.coderadar.graph.query;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.analyzer.domain.MetricValueDTO;
import io.reflectoring.coderadar.graph.query.repository.GetMetricValuesOfCommitRepository;
import io.reflectoring.coderadar.graph.query.service.GetMetricValuesOfCommitAdapter;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Get metric values of commit")
class GetMetricValuesOfCommitAdapterTest {
  private GetMetricValuesOfCommitRepository getMetricValuesOfCommitRepository =
      mock(GetMetricValuesOfCommitRepository.class);
  private GetMetricValuesOfCommitAdapter getMetricValuesOfCommitAdapter;

  @Test
  @DisplayName("Should return list when passing a valid argument")
  void shouldReturnListWhenPassingAValidArgument() {
    getMetricValuesOfCommitAdapter = new GetMetricValuesOfCommitAdapter();

    List<MetricValueDTO> returnedList = getMetricValuesOfCommitAdapter.get("1A");
    Assertions.assertThat(returnedList).isNotNull();
  }
}
