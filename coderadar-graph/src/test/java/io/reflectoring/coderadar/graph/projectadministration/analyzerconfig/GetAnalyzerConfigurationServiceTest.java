package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.GetAnalyzerConfigurationRepository;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.service.GetAnalyzerConfigurationService;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName("Get analyzer configuration")
class GetAnalyzerConfigurationServiceTest {
  @Mock private GetAnalyzerConfigurationRepository getAnalyzerConfigurationRepository;

  @InjectMocks private GetAnalyzerConfigurationService getAnalyzerConfigurationService;

  @Test
  @DisplayName(
      "Should return analyzer configuration as optional when a analyzer configuration with the passing ID exists")
  void shouldReturnAnalzerConfigurationAsOptionalWhenAAnalzerConfigurationWithThePassingIdExists() {
    AnalyzerConfiguration mockedItem = new AnalyzerConfiguration();
    mockedItem.setId(1L);
    when(getAnalyzerConfigurationRepository.findById(any(Long.class)))
        .thenReturn(Optional.of(mockedItem));

    Optional<AnalyzerConfiguration> returned =
        getAnalyzerConfigurationService.getAnalyzerConfiguration(1L);

    verify(getAnalyzerConfigurationRepository, times(1)).findById(1L);
    verifyNoMoreInteractions(getAnalyzerConfigurationRepository);
    Assertions.assertTrue(returned.isPresent());
    Assertions.assertEquals(new Long(1L), returned.get().getId());
  }

  @Test
  @DisplayName(
      "Should return analyzer configuration as empty optional when a analyzer configuration with the passing ID doesn't exists")
  void
      shouldReturnAnalyzerConfigurationAsEmptyOptionalWhenAAnalzerConfigurationWithThePassingIdDoesntExists() {
    Optional<AnalyzerConfiguration> mockedItem = Optional.empty();
    when(getAnalyzerConfigurationRepository.findById(any(Long.class))).thenReturn(mockedItem);

    Optional<AnalyzerConfiguration> returned =
        getAnalyzerConfigurationService.getAnalyzerConfiguration(1L);

    verify(getAnalyzerConfigurationRepository, times(1)).findById(1L);
    verifyNoMoreInteractions(getAnalyzerConfigurationRepository);
    Assertions.assertFalse(returned.isPresent());
  }
}
