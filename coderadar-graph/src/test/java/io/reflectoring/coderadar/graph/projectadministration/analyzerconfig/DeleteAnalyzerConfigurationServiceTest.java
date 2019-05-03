package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig;

import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.DeleteAnalyzerConfigurationRepository;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.service.DeleteAnalyzerConfigurationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class DeleteAnalyzerConfigurationServiceTest {
    @Mock
    private DeleteAnalyzerConfigurationRepository deleteAnalyzerConfigurationRepository;

    @InjectMocks
    private DeleteAnalyzerConfigurationService deleteAnalyzerConfigurationService;

    @Test
    public void withAnalyzerConfigurationIdShouldCallDeleteAnalyzerConfigurationMethodOfRepository() {
        doNothing().when(deleteAnalyzerConfigurationRepository).deleteById(isA(Long.class));
        deleteAnalyzerConfigurationService.deleteAnalyzerConfiguration(1L);
        verify(deleteAnalyzerConfigurationRepository, times(1)).deleteById(any(Long.class));
    }
}
