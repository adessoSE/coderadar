package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.DeleteAnalyzerConfigurationRepository;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.service.DeleteAnalyzerConfigurationAdapter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Delete analyzer configuration")
class DeleteAnalyzerConfigurationAdapterTest {
  private DeleteAnalyzerConfigurationRepository deleteAnalyzerConfigurationRepository =
      mock(DeleteAnalyzerConfigurationRepository.class);

  private DeleteAnalyzerConfigurationAdapter deleteAnalyzerConfigurationAdapter;

  @Test
  @DisplayName(
      "Should delete analyzer configuration when a analyzer configuration with the passing ID exists")
  void shouldDeleteAnalyzerConfigurationWhenAAnalyzerConfigurationWithThePassingIdExists() {
    deleteAnalyzerConfigurationAdapter =
        new DeleteAnalyzerConfigurationAdapter(deleteAnalyzerConfigurationRepository);

    doNothing().when(deleteAnalyzerConfigurationRepository).deleteById(isA(Long.class));
    deleteAnalyzerConfigurationAdapter.deleteAnalyzerConfiguration(1L);
    verify(deleteAnalyzerConfigurationRepository, times(1)).deleteById(any(Long.class));
  }
}
