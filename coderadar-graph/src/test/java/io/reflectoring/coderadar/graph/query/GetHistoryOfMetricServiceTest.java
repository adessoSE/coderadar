package io.reflectoring.coderadar.graph.query;

import io.reflectoring.coderadar.core.query.domain.Interval;
import io.reflectoring.coderadar.core.query.domain.Series;
import io.reflectoring.coderadar.core.query.port.driver.GetHistoryOfMetricCommand;
import io.reflectoring.coderadar.graph.query.repository.GetHistoryOfMetricRepository;
import io.reflectoring.coderadar.graph.query.service.GetHistoryOfMetricService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

@ExtendWith(SpringExtension.class)
@DisplayName("Get history of metric")
public class GetHistoryOfMetricServiceTest {
    @Mock private GetHistoryOfMetricRepository getHistoryOfMetricRepository;
    @InjectMocks private GetHistoryOfMetricService getHistoryOfMetricService;

    @Test
    @DisplayName("Should return series when passing a valid argument")
    void shouldReturnSeriesWhenPassingAValidArgument() {
        GetHistoryOfMetricCommand command = new GetHistoryOfMetricCommand(1L, "loc", new Date(), new Date(), Interval.DAY);
        Series returnedSeries = getHistoryOfMetricService.get(command);
        Assertions.assertThat(returnedSeries).isNotNull();
    }
}
