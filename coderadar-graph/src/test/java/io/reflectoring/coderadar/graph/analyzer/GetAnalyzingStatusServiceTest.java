package io.reflectoring.coderadar.graph.analyzer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.analyzer.AnalyzingJobNotStartedException;
import io.reflectoring.coderadar.analyzer.domain.AnalyzingJob;
import io.reflectoring.coderadar.graph.analyzer.repository.GetAnalyzingStatusRepository;
import io.reflectoring.coderadar.graph.analyzer.service.GetAnalyzingStatusService;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Get analyzing status")
class GetAnalyzingStatusServiceTest {
  private GetAnalyzingStatusRepository getAnalyzingStatusRepository =
      mock(GetAnalyzingStatusRepository.class);

  private GetAnalyzingStatusService getanalyzingStatusService;

  @BeforeEach
  void setUp() {
    getanalyzingStatusService = new GetAnalyzingStatusService(getAnalyzingStatusRepository);
  }

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
