package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.GetAnalyzerConfigurationsFromProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.service.GetAnalyzerConfigurationsFromProjectService;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.mockito.Mockito.*;

@DisplayName("Get analyzer configurations from project")
class ListAnalyzerConfigurationsFromProjectServiceTest {
  private GetAnalyzerConfigurationsFromProjectRepository
      getAnalyzerConfigurationsFromProjectRepository =
          mock(GetAnalyzerConfigurationsFromProjectRepository.class);

  private GetProjectRepository getProjectRepository = mock(GetProjectRepository.class);

  private GetAnalyzerConfigurationsFromProjectService getAnalyzerConfigurationsFromProjectService;

  @BeforeEach
  void setUp() {
    getAnalyzerConfigurationsFromProjectService =
        new GetAnalyzerConfigurationsFromProjectService(
            getProjectRepository, getAnalyzerConfigurationsFromProjectRepository);
  }

  @Test
  @DisplayName("Should throw exception when a project with the passing ID doesn't exists")
  void shouldThrowExceptionWhenAProjectWithThePassingIdDoesntExists() {
    org.junit.jupiter.api.Assertions.assertThrows(
        ProjectNotFoundException.class, () -> getAnalyzerConfigurationsFromProjectService.get(1L));
  }

  @Test
  @DisplayName("Should return empty list when no analyzer configurations in the project exist")
  void shouldReturnEmptyListWhenNoAnalyzerConfigurationsInTheProjectExist() {
    Project mockedProject = new Project();
    when(getProjectRepository.findById(1L)).thenReturn(java.util.Optional.of(mockedProject));
    when(getAnalyzerConfigurationsFromProjectRepository.findByProject_Id(1L))
        .thenReturn(new LinkedList<>());

    Iterable<AnalyzerConfiguration> configurations =
        getAnalyzerConfigurationsFromProjectService.get(1L);
    verify(getAnalyzerConfigurationsFromProjectRepository, times(1)).findByProject_Id(1L);
    Assertions.assertThat(configurations).hasSize(0);
  }

  @Test
  @DisplayName(
      "Should return list of size of one when one analyzer configuration in the project exists")
  void shouldReturnListOfSizeOfOneWhenOneAnalyzerConfigurationInTheProjectExists() {
    Project mockedProject = new Project();
    LinkedList<AnalyzerConfiguration> mockedAnalyzerConfigurations = new LinkedList<>();
    mockedAnalyzerConfigurations.add(new AnalyzerConfiguration());
    when(getProjectRepository.findById(1L)).thenReturn(java.util.Optional.of(mockedProject));
    when(getAnalyzerConfigurationsFromProjectRepository.findByProject_Id(1L))
        .thenReturn(mockedAnalyzerConfigurations);

    Iterable<AnalyzerConfiguration> configurations =
        getAnalyzerConfigurationsFromProjectService.get(1L);
    verify(getAnalyzerConfigurationsFromProjectRepository, times(1)).findByProject_Id(1L);
    Assertions.assertThat(configurations).hasSize(1);
  }

  @Test
  @DisplayName(
      "Should return list of size of two when two analyzer configurations in the project exist")
  void shouldReturnListOfSizeOfTwoWhenTwoAnalzerConfigurationsInTheProjectExist() {
    Project mockedProject = new Project();
    LinkedList<AnalyzerConfiguration> mockedAnalyzerConfigurations = new LinkedList<>();
    mockedAnalyzerConfigurations.add(new AnalyzerConfiguration());
    mockedAnalyzerConfigurations.add(new AnalyzerConfiguration());
    when(getProjectRepository.findById(1L)).thenReturn(java.util.Optional.of(mockedProject));
    when(getAnalyzerConfigurationsFromProjectRepository.findByProject_Id(1L))
        .thenReturn(mockedAnalyzerConfigurations);

    Iterable<AnalyzerConfiguration> configurations =
        getAnalyzerConfigurationsFromProjectService.get(1L);
    verify(getAnalyzerConfigurationsFromProjectRepository, times(1)).findByProject_Id(1L);
    Assertions.assertThat(configurations).hasSize(2);
  }
}
