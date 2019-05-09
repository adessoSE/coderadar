package io.reflectoring.coderadar.graph.analyzer;

import io.reflectoring.coderadar.core.analyzer.AnalyzingJobNotStartedException;
import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzingJob;
import io.reflectoring.coderadar.graph.analyzer.repository.GetAnalyzingStatusRepository;
import io.reflectoring.coderadar.graph.analyzer.service.GetAnalyzingStatusService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Get analyzing status")
class GetAnalyzingStatusServiceTest {
  @Mock private GetAnalyzingStatusRepository getAnalyzingStatusRepository;

  @InjectMocks private GetAnalyzingStatusService getanalyzingStatusService;

  @Test
  @DisplayName("Should throw exception when a analyzing job hasn't been started")
  void shouldThrowExceptionWhenAAnalyzingJobHasntBeenStarted() {
    Assertions.assertThrows(
        AnalyzingJobNotStartedException.class, () -> getanalyzingStatusService.get(1L));
  }

  @Test
  @DisplayName("Should return true when a analyzing job exists in the project with the passing ID")
  void shouldReturnTrueWhenAAnalyzingJobExistsInTheProjectWithThePassingId() {
    AnalyzingJob mockedItem = new AnalyzingJob();
    mockedItem.setId(1L);
    mockedItem.setActive(true);
    when(getAnalyzingStatusRepository.findByProject_Id(any(Long.class)))
        .thenReturn(Optional.of(mockedItem));

    boolean active = getanalyzingStatusService.get(1L);

    verify(getAnalyzingStatusRepository, times(1)).findByProject_Id(1L);
    verifyNoMoreInteractions(getAnalyzingStatusRepository);
    Assertions.assertTrue(active);
  }

  @Test
  @DisplayName(
      "Should return false when a analyzing job doesn't exists in the project with the passing ID")
  void shouldReturnFalseWhenAAnalyzingJobDoesntExistsInTheProjectWithThePassingId() {
    AnalyzingJob mockedItem = new AnalyzingJob();
    mockedItem.setId(1L);
    mockedItem.setActive(false);
    when(getAnalyzingStatusRepository.findByProject_Id(any(Long.class)))
        .thenReturn(Optional.of(mockedItem));

    boolean active = getanalyzingStatusService.get(1L);

    verify(getAnalyzingStatusRepository, times(1)).findByProject_Id(1L);
    verifyNoMoreInteractions(getAnalyzingStatusRepository);
    Assertions.assertFalse(active);
  }
}
