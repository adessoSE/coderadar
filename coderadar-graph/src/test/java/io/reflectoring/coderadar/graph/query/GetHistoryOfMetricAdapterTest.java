package io.reflectoring.coderadar.graph.query;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.graph.query.adapter.GetHistoryOfMetricAdapter;
import io.reflectoring.coderadar.graph.query.repository.GetHistoryOfMetricRepository;
import io.reflectoring.coderadar.query.domain.Interval;
import io.reflectoring.coderadar.query.domain.Series;
import io.reflectoring.coderadar.query.port.driver.GetHistoryOfMetricCommand;
import java.util.Date;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Get history of metric")
class GetHistoryOfMetricAdapterTest {
  private GetHistoryOfMetricRepository getHistoryOfMetricRepository =
      mock(GetHistoryOfMetricRepository.class);

  @Test
  @DisplayName("Should return series when passing a valid argument")
  void shouldReturnSeriesWhenPassingAValidArgument() {
    GetHistoryOfMetricAdapter getHistoryOfMetricAdapter = new GetHistoryOfMetricAdapter();

    GetHistoryOfMetricCommand command =
        new GetHistoryOfMetricCommand("loc", new Date(), new Date(), Interval.DAY);
    Series returnedSeries = getHistoryOfMetricAdapter.get(command);
    Assertions.assertThat(returnedSeries).isNotNull();
  }
}
