package io.reflectoring.coderadar.graph.query;

import io.reflectoring.coderadar.graph.query.repository.GetCommitsInProjectRepository;
import io.reflectoring.coderadar.graph.query.repository.GetMetricValuesOfCommitRepository;
import io.reflectoring.coderadar.graph.query.service.GetMetricValuesOfCommitAdapter;
import io.reflectoring.coderadar.query.domain.MetricValueForCommit;
import io.reflectoring.coderadar.query.port.driver.GetMetricsForCommitCommand;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;

@DisplayName("Get metric values of commit")
class GetMetricValuesOfCommitAdapterTest {
  private GetMetricValuesOfCommitRepository getMetricValuesOfCommitRepository =
      mock(GetMetricValuesOfCommitRepository.class);
  private GetMetricValuesOfCommitAdapter getMetricValuesOfCommitAdapter;
private GetCommitsInProjectRepository getCommitsInProjectRepository = mock(GetCommitsInProjectRepository.class);

/*  @Test TODO: This should be an integration test.
  @DisplayName("Should return list when passing a valid argument")
  void shouldReturnListWhenPassingAValidArgument() {
    getMetricValuesOfCommitAdapter =
        new GetMetricValuesOfCommitAdapter(
            getMetricValuesOfCommitRepository, getCommitsInProjectRepository);

    GetMetricsForCommitCommand command = new GetMetricsForCommitCommand();
    command.setCommit("1A");
    command.setMetrics(Arrays.asList("sloc", "loc"));

    List<MetricValueForCommit> returnedList = getMetricValuesOfCommitAdapter.get(command, 1L);
    Assertions.assertThat(returnedList).isNotNull();
  }*/
}
