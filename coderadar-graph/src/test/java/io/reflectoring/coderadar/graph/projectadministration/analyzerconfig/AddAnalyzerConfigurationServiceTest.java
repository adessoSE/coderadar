package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.graph.exception.InvalidArgumentException;
import io.reflectoring.coderadar.graph.exception.ProjectNotFoundException;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.AddAnalyzerConfigurationRepository;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.service.AddAnalyzerConfigurationService;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AddAnalyzerConfigurationServiceTest {
    @Mock
    private AddAnalyzerConfigurationRepository addAnalyzerConfigurationRepository;

    @Mock
    private GetProjectRepository getProjectRepository;

    @InjectMocks
    private AddAnalyzerConfigurationService addAnalyzerConfigurationService;

    @Test
    public void withInvalidEntityShouldThrowInvalidArgumentException() {
        Assertions.assertThrows(
                InvalidArgumentException.class, () -> addAnalyzerConfigurationService.add(1L, null));
    }

    @Test
    public void withInvalidProjectIdShouldThrowInvalidArgumentException() {
        Assertions.assertThrows(
                InvalidArgumentException.class, () -> addAnalyzerConfigurationService.add(null, new AnalyzerConfiguration()));
    }

    @Test
    public void withNoPersistedProjectShouldThrowProjectNotFoundException() {
        Assertions.assertThrows(
                ProjectNotFoundException.class, () -> addAnalyzerConfigurationService.add(1L, new AnalyzerConfiguration()));
    }

    @Test
    public void withValidEntityShouldReturnIdFromPersistedEntity() {
        AnalyzerConfiguration mockItem = new AnalyzerConfiguration();
        mockItem.setId(10L);
        Project mockProject = new Project();
        mockProject.setId(1L);
        when(getProjectRepository.findById(anyLong())).thenReturn(java.util.Optional.of(mockProject));
        when(addAnalyzerConfigurationRepository.save(any(AnalyzerConfiguration.class))).thenReturn(mockItem);

        AnalyzerConfiguration item = new AnalyzerConfiguration();
        Long idFromItem = addAnalyzerConfigurationService.add(1L, item);

        verify(getProjectRepository, times(1)).findById(1L);
        verify(addAnalyzerConfigurationRepository, times(1)).save(item);
        verifyNoMoreInteractions(getProjectRepository);
        verifyNoMoreInteractions(addAnalyzerConfigurationRepository);
        org.assertj.core.api.Assertions.assertThat(idFromItem).isEqualTo(10L);
    }
}
