package io.reflectoring.coderadar.rest.project;

import io.reflectoring.coderadar.core.projectadministration.port.driver.project.DeleteProjectUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class DeleteProjectControllerTest {

    @Mock
    private DeleteProjectUseCase deleteProjectUseCase;
    private DeleteProjectController testSubject;

    @BeforeEach
    public void setup() {
        testSubject = new DeleteProjectController(deleteProjectUseCase);
    }

    @Test
    public void deleteProjectWithIdOne() {
        // TODO
    }

    @Test
    public void throwsExceptionIfProjectDoesNotExist() {
        // TODO
    }
}
