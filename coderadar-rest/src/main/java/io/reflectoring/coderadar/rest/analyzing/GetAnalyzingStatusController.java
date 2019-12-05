package io.reflectoring.coderadar.rest.analyzing;

import io.reflectoring.coderadar.analyzer.port.driver.GetAnalyzingStatusUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Transactional
public class GetAnalyzingStatusController {
  private final GetAnalyzingStatusUseCase getAnalyzingStatusUseCase;

  public GetAnalyzingStatusController(GetAnalyzingStatusUseCase getAnalyzingStatusUseCase) {
    this.getAnalyzingStatusUseCase = getAnalyzingStatusUseCase;
  }

  @GetMapping(path = "/projects/{projectId}/analyzingStatus")
  public ResponseEntity getProjectAnalyzingStatus(@PathVariable("projectId") Long projectId){
    Map<String, Boolean> response = new HashMap<>();
    response.put("status", this.getAnalyzingStatusUseCase.get(projectId));
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
