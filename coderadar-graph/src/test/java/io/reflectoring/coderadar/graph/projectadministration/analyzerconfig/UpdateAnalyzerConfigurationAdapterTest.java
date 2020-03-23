package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.graph.analyzer.domain.AnalyzerConfigurationEntity;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.adapter.UpdateAnalyzerConfigurationAdapter;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.AnalyzerConfigurationRepository;
import io.reflectoring.coderadar.projectadministration.AnalyzerConfigurationNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Update analyzer configuration")
class UpdateAnalyzerConfigurationAdapterTest {
  private AnalyzerConfigurationRepository analyzerConfigurationRepository =
      mock(AnalyzerConfigurationRepository.class);

  private UpdateAnalyzerConfigurationAdapter updateAnalyzerConfigurationAdapter;

  @BeforeEach
  void setUp() {
    updateAnalyzerConfigurationAdapter =
        new UpdateAnalyzerConfigurationAdapter(analyzerConfigurationRepository);
  }

  @Test
  @DisplayName(
      "Should throw exception when a analyzer configuration with the passing ID doesn't exists")
  void shouldThrowExceptionWhenAAnalyzerConfigurationWithThePassingIdDoesntExists() {
    Assertions.assertThrows(
        AnalyzerConfigurationNotFoundException.class,
        () -> updateAnalyzerConfigurationAdapter.update(new AnalyzerConfiguration()));
  }

  @Test
  @DisplayName(
      "Should update analyzer configuration when a analyzer configuration with the passing ID exists")
  void shouldUpdateAnalyzerConfigurationWhenAAnalyzerConfigurationWithThePassingIdExists() {
    AnalyzerConfigurationEntity mockedOldItem = new AnalyzerConfigurationEntity();
    mockedOldItem.setId(1L);
    mockedOldItem.setAnalyzerName("LoC");
    when(analyzerConfigurationRepository.findById(any(Long.class)))
        .thenReturn(Optional.of(mockedOldItem));

    AnalyzerConfigurationEntity mockedItem = new AnalyzerConfigurationEntity();
    mockedItem.setId(1L);
    mockedItem.setAnalyzerName("SLoC");
    mockedItem.setEnabled(false);
    when(analyzerConfigurationRepository.save(any(AnalyzerConfigurationEntity.class)))
        .thenReturn(mockedItem);

    AnalyzerConfiguration newItem = new AnalyzerConfiguration();
    newItem.setId(1L);
    newItem.setAnalyzerName("SLoC");
    updateAnalyzerConfigurationAdapter.update(newItem);

    verify(analyzerConfigurationRepository, times(1)).save(mockedItem);
    Assertions.assertNotEquals(mockedOldItem, newItem);
  }
}
