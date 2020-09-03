package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig;

import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.graph.analyzer.domain.AnalyzerConfigurationEntity;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.adapter.GetAnalyzerConfigurationAdapter;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.AnalyzerConfigurationRepository;
import io.reflectoring.coderadar.projectadministration.AnalyzerConfigurationNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Get analyzer configuration")
class GetAnalyzerConfigurationAdapterTest {
  private final AnalyzerConfigurationRepository analyzerConfigurationRepository =
      mock(AnalyzerConfigurationRepository.class);

  private GetAnalyzerConfigurationAdapter getAnalyzerConfigurationAdapter;

  @BeforeEach
  void setUp() {
    getAnalyzerConfigurationAdapter =
        new GetAnalyzerConfigurationAdapter(analyzerConfigurationRepository);
  }

  @Test
  @DisplayName(
      "Should return analyzer configuration as optional when a analyzer configuration with the passing ID exists")
  void
      shouldReturnAnalyzerConfigurationAsOptionalWhenAAnalyzerConfigurationWithThePassingIdExists() {
    AnalyzerConfigurationEntity mockedItem = new AnalyzerConfigurationEntity();
    mockedItem.setEnabled(true);
    mockedItem.setId(1L);
    when(analyzerConfigurationRepository.findById(anyLong(), anyInt()))
        .thenReturn(Optional.of(mockedItem));

    AnalyzerConfiguration returned = getAnalyzerConfigurationAdapter.getAnalyzerConfiguration(1L);

    verify(analyzerConfigurationRepository, times(1)).findById(1L, 0);
    verifyNoMoreInteractions(analyzerConfigurationRepository);
    Assertions.assertNotNull(returned);
    Assertions.assertEquals(1L, returned.getId());
  }

  @Test
  @DisplayName(
      "Should return analyzer configuration as empty optional when a analyzer configuration with the passing ID doesn't exists")
  void
      shouldReturnAnalyzerConfigurationAsEmptyOptionalWhenAAnalyzerConfigurationWithThePassingIdDoesntExists() {
    Optional<AnalyzerConfigurationEntity> mockedItem = Optional.empty();
    when(analyzerConfigurationRepository.findById(anyLong())).thenReturn(mockedItem);

    Assertions.assertThrows(
        AnalyzerConfigurationNotFoundException.class,
        () -> getAnalyzerConfigurationAdapter.getAnalyzerConfiguration(1L));
    verify(analyzerConfigurationRepository, times(1)).findById(1L, 0);
    verifyNoMoreInteractions(analyzerConfigurationRepository);
  }
}
