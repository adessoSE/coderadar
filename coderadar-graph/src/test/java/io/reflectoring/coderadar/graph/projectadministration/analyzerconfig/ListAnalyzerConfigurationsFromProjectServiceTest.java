package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig;

import static org.mockito.Mockito.*;

import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.graph.analyzer.domain.AnalyzerConfigurationEntity;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.AnalyzerConfigurationRepository;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.service.GetAnalyzerConfigurationsFromProjectAdapter;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
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

  private GetAnalyzerConfigurationsFromProjectAdapter getAnalyzerConfigurationsFromProjectAdapter;

  @BeforeEach
  void setUp() {
    getAnalyzerConfigurationsFromProjectAdapter =
        new GetAnalyzerConfigurationsFromProjectAdapter(
            projectRepository, analyzerConfigurationRepository);
  }

  @Test
  @DisplayName("Should throw exception when a project with the passing ID doesn't exists")
  void shouldThrowExceptionWhenAProjectWithThePassingIdDoesntExists() {
    org.junit.jupiter.api.Assertions.assertThrows(
        ProjectNotFoundException.class, () -> getAnalyzerConfigurationsFromProjectAdapter.get(1L));
  }

  @Test
  @DisplayName("Should return empty list when no analyzer configurations in the project exist")
  void shouldReturnEmptyListWhenNoAnalyzerConfigurationsInTheProjectExist() {
    ProjectEntity mockedProject = new ProjectEntity();
    when(projectRepository.existsById(1L)).thenReturn(true);
    when(analyzerConfigurationRepository.findByProjectId(1L)).thenReturn(new LinkedList<>());

    Iterable<AnalyzerConfiguration> configurations =
        getAnalyzerConfigurationsFromProjectAdapter.get(1L);
    verify(analyzerConfigurationRepository, times(1)).findByProjectId(1L);
    Assertions.assertThat(configurations).hasSize(0);
  }

  @Test
  @DisplayName(
      "Should return list of size of one when one analyzer configuration in the project exists")
  void shouldReturnListOfSizeOfOneWhenOneAnalyzerConfigurationInTheProjectExists() {
    ProjectEntity mockedProject = new ProjectEntity();
    LinkedList<AnalyzerConfigurationEntity> mockedAnalyzerConfigurations = new LinkedList<>();
    mockedAnalyzerConfigurations.add(new AnalyzerConfigurationEntity());
    when(projectRepository.existsById(1L)).thenReturn(true);
    when(analyzerConfigurationRepository.findByProjectId(1L))
        .thenReturn(mockedAnalyzerConfigurations);

    Iterable<AnalyzerConfiguration> configurations =
        getAnalyzerConfigurationsFromProjectAdapter.get(1L);
    verify(analyzerConfigurationRepository, times(1)).findByProjectId(1L);
    Assertions.assertThat(configurations).hasSize(1);
  }

  @Test
  @DisplayName(
      "Should return list of size of two when two analyzer configurations in the project exist")
  void shouldReturnListOfSizeOfTwoWhenTwoAnalyzerConfigurationsInTheProjectExist() {
    ProjectEntity mockedProject = new ProjectEntity();
    LinkedList<AnalyzerConfigurationEntity> mockedAnalyzerConfigurations = new LinkedList<>();
    mockedAnalyzerConfigurations.add(new AnalyzerConfigurationEntity());
    mockedAnalyzerConfigurations.add(new AnalyzerConfigurationEntity());
    when(projectRepository.existsById(1L)).thenReturn(true);
    when(analyzerConfigurationRepository.findByProjectId(1L))
        .thenReturn(mockedAnalyzerConfigurations);

    Iterable<AnalyzerConfiguration> configurations =
        getAnalyzerConfigurationsFromProjectAdapter.get(1L);
    verify(analyzerConfigurationRepository, times(1)).findByProjectId(1L);
    Assertions.assertThat(configurations).hasSize(2);
  }
}
