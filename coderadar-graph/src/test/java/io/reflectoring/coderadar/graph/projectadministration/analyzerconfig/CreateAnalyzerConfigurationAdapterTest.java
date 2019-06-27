package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig;

import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.CreateAnalyzerConfigurationRepository;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.service.CreateAnalyzerConfigurationAdapter;
import io.reflectoring.coderadar.graph.projectadministration.domain.AnalyzerConfigurationEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Add analyzer configuration")
class CreateAnalyzerConfigurationAdapterTest {
  private CreateAnalyzerConfigurationRepository createAnalyzerConfigurationRepository =
      mock(CreateAnalyzerConfigurationRepository.class);
  private GetProjectRepository getProjectRepository = mock(GetProjectRepository.class);

  @Test
  @DisplayName("Should return ID when saving an analyzer configuration")
  void shouldReturnIdWhenSavingAnAnalyzerConfiguration() {
    CreateAnalyzerConfigurationAdapter createAnalyzerConfigurationAdapter =
        new CreateAnalyzerConfigurationAdapter(
            createAnalyzerConfigurationRepository, getProjectRepository);

    AnalyzerConfigurationEntity mockItem = new AnalyzerConfigurationEntity();
    mockItem.setId(10L);
    Project mockProject = new Project();
    mockProject.setId(1L);
    when(createAnalyzerConfigurationRepository.save(any(AnalyzerConfigurationEntity.class)))
        .thenReturn(mockItem);

    when(getProjectRepository.findById(anyLong()))
        .thenReturn(java.util.Optional.of(new ProjectEntity()));

    AnalyzerConfiguration item = new AnalyzerConfiguration();
    Long idFromItem = createAnalyzerConfigurationAdapter.create(item, 1L);

    verify(createAnalyzerConfigurationRepository, times(1)).save(any());
    verifyNoMoreInteractions(createAnalyzerConfigurationRepository);
    org.assertj.core.api.Assertions.assertThat(idFromItem).isEqualTo(10L);
  }
}
