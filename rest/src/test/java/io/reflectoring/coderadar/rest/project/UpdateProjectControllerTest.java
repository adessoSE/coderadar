package io.reflectoring.coderadar.rest.project;

import io.reflectoring.coderadar.core.projectadministration.port.driver.project.update.UpdateProjectUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class UpdateProjectControllerTest {

  @Mock private UpdateProjectUseCase updateProjectUseCase;
  private UpdateProjectController testSubject;

  @BeforeEach
  public void setup() {
    testSubject = new UpdateProjectController(updateProjectUseCase);
  }

  @Test
  public void updateProjectWithIdOne() {
    // TODO
  }
}
