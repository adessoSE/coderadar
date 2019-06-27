package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.GetAnalyzerConfigurationRepository;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.service.GetAnalyzerConfigurationAdapter;
import io.reflectoring.coderadar.graph.projectadministration.domain.AnalyzerConfigurationEntity;
import io.reflectoring.coderadar.projectadministration.AnalyzerConfigurationNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.AnalyzerConfiguration;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Get analyzer configuration")
class GetAnalyzerConfigurationAdapterTest {
  private GetAnalyzerConfigurationRepository getAnalyzerConfigurationRepository =
      mock(GetAnalyzerConfigurationRepository.class);

  private GetAnalyzerConfigurationAdapter getAnalyzerConfigurationAdapter;

  @BeforeEach
  void setUp() {
    getAnalyzerConfigurationAdapter =
        new GetAnalyzerConfigurationAdapter(getAnalyzerConfigurationRepository);
  }

  @Test
  @DisplayName(
      "Should return analyzer configuration as optional when a analyzer configuration with the passing ID exists")
  void shouldReturnAnalzerConfigurationAsOptionalWhenAAnalzerConfigurationWithThePassingIdExists() {
    AnalyzerConfigurationEntity mockedItem = new AnalyzerConfigurationEntity();
    mockedItem.setId(1L);
    when(getAnalyzerConfigurationRepository.findById(any(Long.class)))
        .thenReturn(Optional.of(mockedItem));

    AnalyzerConfiguration returned = getAnalyzerConfigurationAdapter.getAnalyzerConfiguration(1L);

    verify(getAnalyzerConfigurationRepository, times(1)).findById(1L);
    verifyNoMoreInteractions(getAnalyzerConfigurationRepository);
    Assertions.assertNotNull(returned);
    Assertions.assertEquals(new Long(1L), returned.getId());
  }

  @Test
  @DisplayName(
      "Should return analyzer configuration as empty optional when a analyzer configuration with the passing ID doesn't exists")
  void
      shouldReturnAnalyzerConfigurationAsEmptyOptionalWhenAAnalzerConfigurationWithThePassingIdDoesntExists() {
    Optional<AnalyzerConfigurationEntity> mockedItem = Optional.empty();
    when(getAnalyzerConfigurationRepository.findById(any(Long.class))).thenReturn(mockedItem);

    Assertions.assertThrows(
        AnalyzerConfigurationNotFoundException.class,
        () -> getAnalyzerConfigurationAdapter.getAnalyzerConfiguration(1L));
    verify(getAnalyzerConfigurationRepository, times(1)).findById(1L);
    verifyNoMoreInteractions(getAnalyzerConfigurationRepository);
  }
}
