package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.adapter.DeleteAnalyzerConfigurationAdapter;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.AnalyzerConfigurationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Delete analyzer configuration")
class DeleteAnalyzerConfigurationAdapterTest {
  private AnalyzerConfigurationRepository analyzerConfigurationRepository =
      mock(AnalyzerConfigurationRepository.class);

  private DeleteAnalyzerConfigurationAdapter deleteAnalyzerConfigurationAdapter;

  @Test
  @DisplayName(
      "Should delete analyzer configuration when a analyzer configuration with the passing ID exists")
  void shouldDeleteAnalyzerConfigurationWhenAAnalyzerConfigurationWithThePassingIdExists() {
    deleteAnalyzerConfigurationAdapter =
        new DeleteAnalyzerConfigurationAdapter(analyzerConfigurationRepository);
    when(analyzerConfigurationRepository.existsById(anyLong())).thenReturn(true);
    doNothing().when(analyzerConfigurationRepository).deleteById(isA(Long.class));
    deleteAnalyzerConfigurationAdapter.deleteAnalyzerConfiguration(1L);
    verify(analyzerConfigurationRepository, times(1)).deleteById(any(Long.class));
  }
}
