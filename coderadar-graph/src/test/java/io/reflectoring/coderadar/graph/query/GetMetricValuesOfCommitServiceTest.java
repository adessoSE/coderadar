package io.reflectoring.coderadar.graph.query;

import io.reflectoring.coderadar.core.analyzer.domain.MetricValueDTO;
import io.reflectoring.coderadar.graph.query.repository.GetMetricValuesOfCommitRepository;
import io.reflectoring.coderadar.graph.query.service.GetMetricValuesOfCommitService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DisplayName("Get metric values of commit")
public class GetMetricValuesOfCommitServiceTest {
    @Mock private GetMetricValuesOfCommitRepository getMetricValuesOfCommitRepository;
    @InjectMocks private GetMetricValuesOfCommitService getMetricValuesOfCommitService;

    @Test
    @DisplayName("Should return list when passing a valid argument")
    void shouldReturnListWhenPassingAValidArgument() {
        List<MetricValueDTO> returnedList = getMetricValuesOfCommitService.get("1A");
        Assertions.assertThat(returnedList).isNotNull();
    }
}
