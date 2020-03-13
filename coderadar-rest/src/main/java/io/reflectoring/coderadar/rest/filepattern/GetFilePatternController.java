package io.reflectoring.coderadar.rest.filepattern;

import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.get.GetFilePatternUseCase;
import io.reflectoring.coderadar.rest.domain.GetFilePatternResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class GetFilePatternController {
  private final GetFilePatternUseCase getFilePatternUseCase;

  public GetFilePatternController(GetFilePatternUseCase getFilePatternUseCase) {
    this.getFilePatternUseCase = getFilePatternUseCase;
  }

  @GetMapping(
      path = "/projects/{projectId}/filePatterns/{filePatternId}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<GetFilePatternResponse> getFilePattern(
      @PathVariable(name = "filePatternId") long filePatternId) {
    FilePattern filePattern = getFilePatternUseCase.get(filePatternId);
    return new ResponseEntity<>(
        new GetFilePatternResponse(
            filePatternId, filePattern.getPattern(), filePattern.getInclusionType()),
        HttpStatus.OK);
  }
}
