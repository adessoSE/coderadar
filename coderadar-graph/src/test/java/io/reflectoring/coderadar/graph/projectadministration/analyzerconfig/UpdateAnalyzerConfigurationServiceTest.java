package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.AnalyzerConfigurationNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.GetAnalyzerConfigurationsFromProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.UpdateAnalyzerConfigurationRepository;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.service.UpdateAnalyzerConfigurationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Update analyzer configuration")
class UpdateAnalyzerConfigurationServiceTest {
    @Mock
    private UpdateAnalyzerConfigurationRepository updateAnalyzerConfigurationRepository;

    @Mock
    private GetAnalyzerConfigurationsFromProjectRepository getAnalyzerConfigurationsFromProjectRepository;

    @InjectMocks
    private UpdateAnalyzerConfigurationService updateAnalyzerConfigurationService;

    @Test
    @DisplayName("Should throw exception when a analyzer configuration with the passing ID doesn't exists")
    void shouldThrowExceptionWhenAAnalyzerConfigurationWithThePassingIdDoesntExists() {
        Assertions.assertThrows(
                AnalyzerConfigurationNotFoundException.class, () -> updateAnalyzerConfigurationService.update(new AnalyzerConfiguration()));
    }

    @Test
    @DisplayName("Should update analyzer configuration when a analyzer configuration with the passing ID exists")
    void shouldUpdateAnalyzerConfigurationWhenAAnalyzerConfigurationWithThePassingIdExists() {
        AnalyzerConfiguration mockedOldItem = new AnalyzerConfiguration();
        mockedOldItem.setId(1L);
        mockedOldItem.setAnalyzerName("LoC");
        when(getAnalyzerConfigurationsFromProjectRepository.findById(any(Long.class))).thenReturn(Optional.of(mockedOldItem));

        AnalyzerConfiguration mockedItem = new AnalyzerConfiguration();
        mockedItem.setId(1L);
        mockedItem.setAnalyzerName("SLoC");
        when(updateAnalyzerConfigurationRepository.save(any(AnalyzerConfiguration.class))).thenReturn(mockedItem);

        AnalyzerConfiguration newItem = new AnalyzerConfiguration();
        newItem.setId(1L);
        newItem.setAnalyzerName("SLoC");
        updateAnalyzerConfigurationService.update(newItem);

        verify(updateAnalyzerConfigurationRepository, times(1)).save(mockedItem);
        Assertions.assertNotEquals(mockedOldItem, newItem);
    }
}
