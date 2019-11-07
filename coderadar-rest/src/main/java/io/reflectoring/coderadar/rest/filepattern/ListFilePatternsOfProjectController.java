package io.reflectoring.coderadar.rest.filepattern;

import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.get.ListFilePatternsOfProjectUseCase;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  public ListFilePatternsOfProjectController(
      ListFilePatternsOfProjectUseCase listFilePatternsOfProjectUseCase) {
    this.listFilePatternsOfProjectUseCase = listFilePatternsOfProjectUseCase;
  }

  @GetMapping(path = "/projects/{projectId}/filePatterns", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity listFilePatterns(@PathVariable Long projectId) {
    return new ResponseEntity<>(listFilePatternsOfProjectUseCase.listFilePatterns(projectId), HttpStatus.OK);
  }
}
