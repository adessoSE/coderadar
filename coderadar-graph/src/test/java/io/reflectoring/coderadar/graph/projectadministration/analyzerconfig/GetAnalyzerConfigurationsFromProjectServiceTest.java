package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.graph.exception.ProjectNotFoundException;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.GetAnalyzerConfigurationsFromProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.service.GetAnalyzerConfigurationsFromProjectService;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.LinkedList;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class GetAnalyzerConfigurationsFromProjectServiceTest {
    @Mock
    private GetAnalyzerConfigurationsFromProjectRepository getAnalyzerConfigurationsFromProjectRepository;

    @Mock
    private GetProjectRepository getProjectRepository;

    @InjectMocks
    private GetAnalyzerConfigurationsFromProjectService getAnalyzerConfigurationsFromProjectService;

    @Test
    public void withNoPersistedProjectShouldThrowProjectNotFoundException() {
        org.junit.jupiter.api.Assertions.assertThrows(
                ProjectNotFoundException.class, () -> getAnalyzerConfigurationsFromProjectService.get(1L));
    }

    @Test
    public void withNoAnalyzerConfigurationsShouldReturnEmptyList() {
        Project mockedProject = new Project();
        when(getProjectRepository.findById(1L)).thenReturn(java.util.Optional.of(mockedProject));
        when(getAnalyzerConfigurationsFromProjectRepository.findByProject_Id(1L)).thenReturn(new LinkedList<>());

        Iterable<AnalyzerConfiguration> configurations = getAnalyzerConfigurationsFromProjectService.get(1L);
        verify(getAnalyzerConfigurationsFromProjectRepository, times(1)).findByProject_Id(1L);
        Assertions.assertThat(configurations).hasSize(0);
    }

    @Test
    public void withOnePersistedAnalyzerConfigurationsShouldReturnListOfSizeOne() {
        Project mockedProject = new Project();
        LinkedList<AnalyzerConfiguration> mockedAnalyzerConfigurations = new LinkedList<>();
        mockedAnalyzerConfigurations.add(new AnalyzerConfiguration());
        when(getProjectRepository.findById(1L)).thenReturn(java.util.Optional.of(mockedProject));
        when(getAnalyzerConfigurationsFromProjectRepository.findByProject_Id(1L)).thenReturn(mockedAnalyzerConfigurations);

        Iterable<AnalyzerConfiguration> configurations = getAnalyzerConfigurationsFromProjectService.get(1L);
        verify(getAnalyzerConfigurationsFromProjectRepository, times(1)).findByProject_Id(1L);
        Assertions.assertThat(configurations).hasSize(1);
    }

    @Test
    public void withTwoPersistedAnalyzerConfigurationsShouldReturnListOfSizeTwo() {
        Project mockedProject = new Project();
        LinkedList<AnalyzerConfiguration> mockedAnalyzerConfigurations = new LinkedList<>();
        mockedAnalyzerConfigurations.add(new AnalyzerConfiguration());
        mockedAnalyzerConfigurations.add(new AnalyzerConfiguration());
        when(getProjectRepository.findById(1L)).thenReturn(java.util.Optional.of(mockedProject));
        when(getAnalyzerConfigurationsFromProjectRepository.findByProject_Id(1L)).thenReturn(mockedAnalyzerConfigurations);

        Iterable<AnalyzerConfiguration> configurations = getAnalyzerConfigurationsFromProjectService.get(1L);
        verify(getAnalyzerConfigurationsFromProjectRepository, times(1)).findByProject_Id(1L);
        Assertions.assertThat(configurations).hasSize(2);
    }
}
