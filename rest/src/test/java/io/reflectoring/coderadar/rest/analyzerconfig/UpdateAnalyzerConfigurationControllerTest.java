package io.reflectoring.coderadar.rest.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.UpdateAnalyzerConfigurationUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class UpdateAnalyzerConfigurationControllerTest {

    @Mock
    private UpdateAnalyzerConfigurationUseCase updateAnalyzerConfigurationUseCase;
    private UpdateAnalyzerConfigurationController testSubject;

    @BeforeEach
    public void setup() {
        testSubject = new UpdateAnalyzerConfigurationController(updateAnalyzerConfigurationUseCase);
    }

    @Test
    public void updateAnalyzerConfigurationWithIdOne() {
        // TODO
    }
}
