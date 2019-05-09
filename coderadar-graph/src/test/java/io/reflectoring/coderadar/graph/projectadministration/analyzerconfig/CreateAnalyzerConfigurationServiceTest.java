package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.CreateAnalyzerConfigurationRepository;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.service.CreateAnalyzerConfigurationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Add analyzer configuration")
class CreateAnalyzerConfigurationServiceTest {
  @Mock private CreateAnalyzerConfigurationRepository createAnalyzerConfigurationRepository;

  @InjectMocks private CreateAnalyzerConfigurationService createAnalyzerConfigurationService;

  @Test
  @DisplayName("Should return ID when saving an analyzer configuration")
  void shouldReturnIdWhenSavingAnAnalyzerConfiguration() {
    AnalyzerConfiguration mockItem = new AnalyzerConfiguration();
    mockItem.setId(10L);
    Project mockProject = new Project();
    mockProject.setId(1L);
    when(createAnalyzerConfigurationRepository.save(any(AnalyzerConfiguration.class)))
        .thenReturn(mockItem);

    AnalyzerConfiguration item = new AnalyzerConfiguration();
    Long idFromItem = createAnalyzerConfigurationService.create(item);

    verify(createAnalyzerConfigurationRepository, times(1)).save(item);
    verifyNoMoreInteractions(createAnalyzerConfigurationRepository);
    org.assertj.core.api.Assertions.assertThat(idFromItem).isEqualTo(10L);
  }
}
