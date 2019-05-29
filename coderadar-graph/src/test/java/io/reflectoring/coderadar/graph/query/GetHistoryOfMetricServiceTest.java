package io.reflectoring.coderadar.graph.query;

import io.reflectoring.coderadar.core.query.domain.Interval;
import io.reflectoring.coderadar.core.query.domain.Series;
import io.reflectoring.coderadar.core.query.port.driver.GetHistoryOfMetricCommand;
import io.reflectoring.coderadar.graph.query.repository.GetHistoryOfMetricRepository;
import io.reflectoring.coderadar.graph.query.service.GetHistoryOfMetricService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.mockito.Mockito.mock;

@DisplayName("Get history of metric")
class GetHistoryOfMetricServiceTest {
  private GetHistoryOfMetricRepository getHistoryOfMetricRepository =
      mock(GetHistoryOfMetricRepository.class);

  @Test
  @DisplayName("Should return series when passing a valid argument")
  void shouldReturnSeriesWhenPassingAValidArgument() {
    GetHistoryOfMetricService getHistoryOfMetricService = new GetHistoryOfMetricService();

    GetHistoryOfMetricCommand command =
        new GetHistoryOfMetricCommand(1L, "loc", new Date(), new Date(), Interval.DAY);
    Series returnedSeries = getHistoryOfMetricService.get(command);
    Assertions.assertThat(returnedSeries).isNotNull();
  }
}
