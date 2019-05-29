package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig;

import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.DeleteAnalyzerConfigurationRepository;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.service.DeleteAnalyzerConfigurationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@DisplayName("Delete analyzer configuration")
class DeleteAnalyzerConfigurationServiceTest {
  private DeleteAnalyzerConfigurationRepository deleteAnalyzerConfigurationRepository =
      mock(DeleteAnalyzerConfigurationRepository.class);

  private DeleteAnalyzerConfigurationService deleteAnalyzerConfigurationService;

  @Test
  @DisplayName(
      "Should delete analyzer configuration when a analyzer configuration with the passing ID exists")
  void shouldDeleteAnalyzerConfigurationWhenAAnalyzerConfigurationWithThePassingIdExists() {
    deleteAnalyzerConfigurationService =
        new DeleteAnalyzerConfigurationService(deleteAnalyzerConfigurationRepository);

    doNothing().when(deleteAnalyzerConfigurationRepository).deleteById(isA(Long.class));
    deleteAnalyzerConfigurationService.deleteAnalyzerConfiguration(1L);
    verify(deleteAnalyzerConfigurationRepository, times(1)).deleteById(any(Long.class));
  }
}
