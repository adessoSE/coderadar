package io.reflectoring.coderadar.rest.user;

import io.reflectoring.coderadar.core.projectadministration.port.driver.user.ChangePasswordUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class ChangePasswordControllerTest {

    @Mock
    private ChangePasswordUseCase changePasswordUseCase;
    private ChangePasswordController testSubject;

    @BeforeEach
    public void setup() {
        testSubject = new ChangePasswordController(changePasswordUseCase);
    }

    @Test
    public void changePasswordSuccessfully() {
        // TODO
    }
}
