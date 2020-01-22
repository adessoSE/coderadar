package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.graph.analyzer.domain.AnalyzerConfigurationEntity;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.adapter.CreateAnalyzerConfigurationAdapter;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.AnalyzerConfigurationRepository;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Add analyzer configuration")
class CreateAnalyzerConfigurationAdapterTest {
  private AnalyzerConfigurationRepository analyzerConfigurationRepository =
      mock(AnalyzerConfigurationRepository.class);
  private ProjectRepository projectRepository = mock(ProjectRepository.class);

  @Test
  @DisplayName("Should return ID when saving an analyzer configuration")
  void shouldReturnIdWhenSavingAnAnalyzerConfiguration() {
    CreateAnalyzerConfigurationAdapter createAnalyzerConfigurationAdapter =
        new CreateAnalyzerConfigurationAdapter(analyzerConfigurationRepository, projectRepository);

    AnalyzerConfigurationEntity mockItem = new AnalyzerConfigurationEntity();
    mockItem.setId(10L);
    Project mockProject = new Project();
    mockProject.setId(1L);
    when(analyzerConfigurationRepository.save(any(AnalyzerConfigurationEntity.class)))
        .thenReturn(mockItem);

    when(projectRepository.findById(anyLong()))
        .thenReturn(java.util.Optional.of(new ProjectEntity()));

    AnalyzerConfiguration item = new AnalyzerConfiguration();
    Long idFromItem = createAnalyzerConfigurationAdapter.create(item, 1L);

    verify(analyzerConfigurationRepository, times(1)).save(any());
    verifyNoMoreInteractions(analyzerConfigurationRepository);
    org.assertj.core.api.Assertions.assertThat(idFromItem).isEqualTo(10L);
  }
}
