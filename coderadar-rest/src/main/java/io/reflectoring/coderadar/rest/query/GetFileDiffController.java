package io.reflectoring.coderadar.rest.query;

import io.reflectoring.coderadar.query.domain.FileContentWithMetrics;
import io.reflectoring.coderadar.query.port.driver.filediff.GetFileDiffCommand;
import io.reflectoring.coderadar.query.port.driver.filediff.GetFileDiffUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Transactional
@RestController
public class GetFileDiffController extends AbstractBaseController {

  private final GetFileDiffUseCase useCase;

  public GetFileDiffController(GetFileDiffUseCase useCase) {
    this.useCase = useCase;
  }

  @RequestMapping(
      method = {RequestMethod.GET, RequestMethod.POST},
      path = "/projects/{projectId}/files/diff",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<FileContentWithMetrics> getFileDiff(
      @PathVariable Long projectId, @RequestBody @Validated GetFileDiffCommand command) {
    return new ResponseEntity<>(useCase.getFileDiff(projectId, command), HttpStatus.OK);
  }
}
