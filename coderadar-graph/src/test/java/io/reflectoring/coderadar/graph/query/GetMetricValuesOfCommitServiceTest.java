package io.reflectoring.coderadar.graph.query;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.analyzer.domain.MetricValueDTO;
import io.reflectoring.coderadar.graph.query.repository.GetMetricValuesOfCommitRepository;
import io.reflectoring.coderadar.graph.query.service.GetMetricValuesOfCommitService;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Get metric values of commit")
class GetMetricValuesOfCommitServiceTest {
  private GetMetricValuesOfCommitRepository getMetricValuesOfCommitRepository =
      mock(GetMetricValuesOfCommitRepository.class);
  private GetMetricValuesOfCommitService getMetricValuesOfCommitService;

  @Test
  @DisplayName("Should return list when passing a valid argument")
  void shouldReturnListWhenPassingAValidArgument() {
    getMetricValuesOfCommitService = new GetMetricValuesOfCommitService();

    List<MetricValueDTO> returnedList = getMetricValuesOfCommitService.get("1A");
    Assertions.assertThat(returnedList).isNotNull();
  }
}
