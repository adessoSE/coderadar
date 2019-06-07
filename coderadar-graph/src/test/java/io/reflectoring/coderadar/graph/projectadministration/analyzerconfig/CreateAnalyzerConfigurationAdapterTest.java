package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.CreateAnalyzerConfigurationRepository;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.service.CreateAnalyzerConfigurationAdapter;
import io.reflectoring.coderadar.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Add analyzer configuration")
class CreateAnalyzerConfigurationAdapterTest {
  private CreateAnalyzerConfigurationRepository createAnalyzerConfigurationRepository =
      mock(CreateAnalyzerConfigurationRepository.class);

  @Test
  @DisplayName("Should return ID when saving an analyzer configuration")
  void shouldReturnIdWhenSavingAnAnalyzerConfiguration() {
    CreateAnalyzerConfigurationAdapter createAnalyzerConfigurationAdapter =
        new CreateAnalyzerConfigurationAdapter(createAnalyzerConfigurationRepository);

    AnalyzerConfiguration mockItem = new AnalyzerConfiguration();
    mockItem.setId(10L);
    Project mockProject = new Project();
    mockProject.setId(1L);
    when(createAnalyzerConfigurationRepository.save(any(AnalyzerConfiguration.class)))
        .thenReturn(mockItem);

    AnalyzerConfiguration item = new AnalyzerConfiguration();
    Long idFromItem = createAnalyzerConfigurationAdapter.create(item);

    verify(createAnalyzerConfigurationRepository, times(1)).save(item);
    verifyNoMoreInteractions(createAnalyzerConfigurationRepository);
    org.assertj.core.api.Assertions.assertThat(idFromItem).isEqualTo(10L);
  }
}
