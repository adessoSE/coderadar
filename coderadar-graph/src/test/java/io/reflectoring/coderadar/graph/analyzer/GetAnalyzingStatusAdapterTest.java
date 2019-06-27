package io.reflectoring.coderadar.graph.analyzer;

import io.reflectoring.coderadar.graph.analyzer.domain.AnalyzingJobEntity;
import io.reflectoring.coderadar.graph.analyzer.repository.GetAnalyzingStatusRepository;
import io.reflectoring.coderadar.graph.analyzer.service.GetAnalyzingStatusAdapter;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Get analyzing status")
class GetAnalyzingStatusAdapterTest {
  private GetAnalyzingStatusRepository getAnalyzingStatusRepository =
      mock(GetAnalyzingStatusRepository.class);

  private GetAnalyzingStatusAdapter getanalyzingStatusAdapter;

  @BeforeEach
  void setUp() {
    getanalyzingStatusAdapter = new GetAnalyzingStatusAdapter(getAnalyzingStatusRepository);
  }

  @Test
  @DisplayName("Should throw exception when a analyzing job hasn't been started")
  void shouldThrowExceptionWhenAnalyzingJobHasntBeenStarted() {
    Assertions.assertThrows(
        ProjectNotFoundException.class, () -> getanalyzingStatusAdapter.get(1L));
  }

  @Test
  @DisplayName("Should return true when a analyzing job exists in the project with the passing ID")
  void shouldReturnTrueWhenAAnalyzingJobExistsInTheProjectWithThePassingId() {
    AnalyzingJobEntity mockedItem = new AnalyzingJobEntity();
    mockedItem.setId(1L);
    mockedItem.setActive(true);
    when(getAnalyzingStatusRepository.findByProjectId(any(Long.class)))
        .thenReturn(Optional.of(mockedItem));

    boolean active = getanalyzingStatusAdapter.get(1L);

    verify(getAnalyzingStatusRepository, times(1)).findByProjectId(1L);
    verifyNoMoreInteractions(getAnalyzingStatusRepository);
    Assertions.assertTrue(active);
  }

  @Test
  @DisplayName(
      "Should return false when a analyzing job doesn't exists in the project with the passing ID")
  void shouldReturnFalseWhenAAnalyzingJobDoesntExistsInTheProjectWithThePassingId() {
    AnalyzingJobEntity mockedItem = new AnalyzingJobEntity();
    mockedItem.setId(1L);
    mockedItem.setActive(false);
    when(getAnalyzingStatusRepository.findByProjectId(any(Long.class)))
        .thenReturn(Optional.of(mockedItem));

    boolean active = getanalyzingStatusAdapter.get(1L);

    verify(getAnalyzingStatusRepository, times(1)).findByProjectId(1L);
    verifyNoMoreInteractions(getAnalyzingStatusRepository);
    Assertions.assertFalse(active);
  }
}
