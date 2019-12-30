package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig;

import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.graph.analyzer.domain.AnalyzerConfigurationEntity;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.adapter.ListAnalyzerConfigurationsAdapter;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.AnalyzerConfigurationRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import java.util.LinkedList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Get analyzer configurations from project")
class ListAnalyzerConfigurationsFromProjectServiceTest {
  private AnalyzerConfigurationRepository analyzerConfigurationRepository =
      mock(AnalyzerConfigurationRepository.class);

  private ProjectRepository projectRepository = mock(ProjectRepository.class);

  private ListAnalyzerConfigurationsAdapter getAnalyzerConfigurationsFromProjectAdapter;

  @BeforeEach
  void setUp() {
    getAnalyzerConfigurationsFromProjectAdapter =
        new ListAnalyzerConfigurationsAdapter(analyzerConfigurationRepository);
  }

  @Test
  @DisplayName("Should return empty list when no analyzer configurations in the project exist")
  void shouldReturnEmptyListWhenNoAnalyzerConfigurationsInTheProjectExist() {
    when(projectRepository.existsById(1L)).thenReturn(true);
    when(analyzerConfigurationRepository.findByProjectId(1L)).thenReturn(new LinkedList<>());

    Iterable<AnalyzerConfiguration> configurations =
        getAnalyzerConfigurationsFromProjectAdapter.listAnalyzerConfigurations(1L);
    verify(analyzerConfigurationRepository, times(1)).findByProjectId(1L);
    Assertions.assertThat(configurations).hasSize(0);
  }

  @Test
  @DisplayName(
      "Should return list of size of one when one analyzer configuration in the project exists")
  void shouldReturnListOfSizeOfOneWhenOneAnalyzerConfigurationInTheProjectExists() {
    LinkedList<AnalyzerConfigurationEntity> mockedAnalyzerConfigurations = new LinkedList<>();
    mockedAnalyzerConfigurations.add(new AnalyzerConfigurationEntity());
    when(projectRepository.existsById(1L)).thenReturn(true);
    when(analyzerConfigurationRepository.findByProjectId(1L))
        .thenReturn(mockedAnalyzerConfigurations);

    Iterable<AnalyzerConfiguration> configurations =
        getAnalyzerConfigurationsFromProjectAdapter.listAnalyzerConfigurations(1L);
    verify(analyzerConfigurationRepository, times(1)).findByProjectId(1L);
    Assertions.assertThat(configurations).hasSize(1);
  }

  @Test
  @DisplayName(
      "Should return list of size of two when two analyzer configurations in the project exist")
  void shouldReturnListOfSizeOfTwoWhenTwoAnalyzerConfigurationsInTheProjectExist() {
    LinkedList<AnalyzerConfigurationEntity> mockedAnalyzerConfigurations = new LinkedList<>();
    mockedAnalyzerConfigurations.add(new AnalyzerConfigurationEntity());
    mockedAnalyzerConfigurations.add(new AnalyzerConfigurationEntity());
    when(projectRepository.existsById(1L)).thenReturn(true);
    when(analyzerConfigurationRepository.findByProjectId(1L))
        .thenReturn(mockedAnalyzerConfigurations);

    Iterable<AnalyzerConfiguration> configurations =
        getAnalyzerConfigurationsFromProjectAdapter.listAnalyzerConfigurations(1L);
    verify(analyzerConfigurationRepository, times(1)).findByProjectId(1L);
    Assertions.assertThat(configurations).hasSize(2);
  }
}
