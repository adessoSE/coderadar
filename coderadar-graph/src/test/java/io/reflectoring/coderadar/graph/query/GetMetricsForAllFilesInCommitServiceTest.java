package io.reflectoring.coderadar.graph.query;

import io.reflectoring.coderadar.core.analyzer.domain.GroupedMetricValueDTO;
import io.reflectoring.coderadar.core.query.port.driver.GetMetricsForAllFilesInCommitCommand;
import io.reflectoring.coderadar.graph.query.repository.GetMetricsForAllFilesInCommitRepository;
import io.reflectoring.coderadar.graph.query.service.GetMetricsForAllFilesInCommitService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.LinkedList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DisplayName("Get metrics for all files in commit")
public class GetMetricsForAllFilesInCommitServiceTest {
    @Mock private GetMetricsForAllFilesInCommitRepository getMetricsForAllFilesInCommitRepository;
    @InjectMocks private GetMetricsForAllFilesInCommitService getMetricsForAllFilesInCommitService;

    @Test
    @DisplayName("Should return list of GroupedMetricValueDTO when passing a valid argument")
    void shouldReturnListOfGroupedMetricValueDTOWhenPassingAValidArgument() {
        GetMetricsForAllFilesInCommitCommand command = new GetMetricsForAllFilesInCommitCommand("1A", new LinkedList<>());
        List<GroupedMetricValueDTO> returnedList = getMetricsForAllFilesInCommitService.get(command);
        Assertions.assertThat(returnedList).isNotNull();
    }
}
