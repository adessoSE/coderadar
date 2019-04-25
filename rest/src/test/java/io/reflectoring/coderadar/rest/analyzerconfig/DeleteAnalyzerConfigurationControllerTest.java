package io.reflectoring.coderadar.rest.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.delete.DeleteAnalyzerConfigurationUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class DeleteAnalyzerConfigurationControllerTest {

  @Mock private DeleteAnalyzerConfigurationUseCase deleteAnalyzerConfigurationUseCase;
  private DeleteAnalyzerConfigurationController testSubject;

  @BeforeEach
  public void setup() {
    testSubject = new DeleteAnalyzerConfigurationController(deleteAnalyzerConfigurationUseCase);
  }

  @Test
  public void deleteAnalyzerConfigurationWithIdOne() {
    // TODO
  }
}
