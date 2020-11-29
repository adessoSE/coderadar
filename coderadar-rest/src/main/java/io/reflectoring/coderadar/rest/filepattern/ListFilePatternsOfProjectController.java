package io.reflectoring.coderadar.rest.filepattern;

import static io.reflectoring.coderadar.rest.GetFilePatternResponseMapper.mapFilePatterns;

import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.get.ListFilePatternsOfProjectUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.domain.GetFilePatternResponse;
import io.reflectoring.coderadar.useradministration.service.security.AuthenticationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ListFilePatternsOfProjectController implements AbstractBaseController {
  private final ListFilePatternsOfProjectUseCase listFilePatternsOfProjectUseCase;
  private final AuthenticationService authenticationService;

  @GetMapping(
      path = "/projects/{projectId}/filePatterns",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<GetFilePatternResponse>> listFilePatterns(
      @PathVariable long projectId) {
    authenticationService.authenticateMember(projectId);
    List<FilePattern> filePatterns = listFilePatternsOfProjectUseCase.listFilePatterns(projectId);
    return new ResponseEntity<>(mapFilePatterns(filePatterns), HttpStatus.OK);
  }
}
