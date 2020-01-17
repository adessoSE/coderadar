package io.reflectoring.coderadar.rest.filepattern;

import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.get.ListFilePatternsOfProjectUseCase;
import io.reflectoring.coderadar.rest.domain.GetFilePatternResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Transactional
public class ListFilePatternsOfProjectController {
  private final ListFilePatternsOfProjectUseCase listFilePatternsOfProjectUseCase;

  public ListFilePatternsOfProjectController(
      ListFilePatternsOfProjectUseCase listFilePatternsOfProjectUseCase) {
    this.listFilePatternsOfProjectUseCase = listFilePatternsOfProjectUseCase;
  }

  @GetMapping(path = "/projects/{projectId}/filePatterns", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<GetFilePatternResponse>> listFilePatterns(@PathVariable Long projectId) {
    return new ResponseEntity<>(listFilePatternsOfProjectUseCase.listFilePatterns(projectId).stream().map(filePattern ->
            new GetFilePatternResponse()
                    .setId(filePattern.getId())
                    .setInclusionType(filePattern.getInclusionType())
                    .setPattern(filePattern.getPattern()))
            .collect(Collectors.toList()), HttpStatus.OK);
  }
}
