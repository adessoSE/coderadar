package io.reflectoring.coderadar.rest.filepattern;

import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.get.ListFilePatternsOfProjectUseCase;
import io.reflectoring.coderadar.rest.domain.GetFilePatternResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class ListFilePatternsOfProjectController {
  private final ListFilePatternsOfProjectUseCase listFilePatternsOfProjectUseCase;

  public ListFilePatternsOfProjectController(
      ListFilePatternsOfProjectUseCase listFilePatternsOfProjectUseCase) {
    this.listFilePatternsOfProjectUseCase = listFilePatternsOfProjectUseCase;
  }

  @GetMapping(
      path = "/projects/{projectId}/filePatterns",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<GetFilePatternResponse>> listFilePatterns(
      @PathVariable long projectId) {
    List<FilePattern> filePatterns = listFilePatternsOfProjectUseCase.listFilePatterns(projectId);
    List<GetFilePatternResponse> responses = new ArrayList<>(filePatterns.size());
    for (FilePattern filePattern : filePatterns) {
      responses.add(
          new GetFilePatternResponse(
              filePattern.getId(), filePattern.getPattern(), filePattern.getInclusionType()));
    }
    return new ResponseEntity<>(responses, HttpStatus.OK);
  }
}
