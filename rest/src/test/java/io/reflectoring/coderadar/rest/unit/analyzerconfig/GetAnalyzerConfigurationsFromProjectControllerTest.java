package io.reflectoring.coderadar.rest.unit.analyzerconfig;

import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.get.GetAnalyzerConfigurationResponse;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.get.GetAnalyzerConfigurationsFromProjectUseCase;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class GetAnalyzerConfigurationsFromProjectControllerTest {

  @Mock
  private GetAnalyzerConfigurationsFromProjectUseCase getAnalyzerConfigurationsFromProjectUseCase;

  @InjectMocks private GetAnalyzerConfigurationsFromProjectController testSubject;

  @Test
  public void returnsTwoGetAnalyzerConfigurationResponsesFromProject() {
    GetAnalyzerConfigurationResponse response1 =
        new GetAnalyzerConfigurationResponse(1L, "analyzer1", true);
    GetAnalyzerConfigurationResponse response2 =
        new GetAnalyzerConfigurationResponse(2L, "analyzer2", false);
    List<GetAnalyzerConfigurationResponse> responses = new ArrayList<>();

    responses.add(response1);
    responses.add(response2);

    Mockito.when(getAnalyzerConfigurationsFromProjectUseCase.get(1L)).thenReturn(responses);

    ResponseEntity<List<GetAnalyzerConfigurationResponse>> responseEntity =
        testSubject.getAnalyzerConfigurationsFromProject(1L);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Assertions.assertEquals(responses.size(), responseEntity.getBody().size());
    Assertions.assertEquals(response1.getId(), responseEntity.getBody().get(0).getId());
    Assertions.assertEquals(
        response1.getAnalyzerName(), responseEntity.getBody().get(0).getAnalyzerName());
    Assertions.assertEquals(response1.getEnabled(), responseEntity.getBody().get(0).getEnabled());
    Assertions.assertEquals(response2.getId(), responseEntity.getBody().get(1).getId());
    Assertions.assertEquals(
        response2.getAnalyzerName(), responseEntity.getBody().get(1).getAnalyzerName());
    Assertions.assertEquals(response2.getEnabled(), responseEntity.getBody().get(1).getEnabled());
  }
}
