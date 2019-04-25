package io.reflectoring.coderadar.rest.module;

import io.reflectoring.coderadar.core.projectadministration.port.driver.module.ListModulesOfProjectUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class ListModulesOfProjectControllerTest {

    @Mock
    private ListModulesOfProjectUseCase listModulesOfProjectUseCase;
    private ListModulesOfProjectController testSubject;

    @BeforeEach
    public void setup() {
        testSubject = new ListModulesOfProjectController(listModulesOfProjectUseCase);
    }

    @Test
    public void returnsModulesForProjectWithIdOne() {
        // TODO
    }
}
