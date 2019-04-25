package io.reflectoring.coderadar.rest.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.GetAnalyzerConfigurationsFromProjectUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class GetAnalyzerConfigurationsFromProjectControllerTest {

    @Mock
    private GetAnalyzerConfigurationsFromProjectUseCase getAnalyzerConfigurationsFromProjectUseCase;
    private GetAnalyzerConfigurationsFromProjectController testSubject;

    @BeforeEach
    public void setup() {
        testSubject = new GetAnalyzerConfigurationsFromProjectController(getAnalyzerConfigurationsFromProjectUseCase);
    }

    @Test
    public void todo() {

    }
}
