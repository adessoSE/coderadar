package io.reflectoring.coderadar.rest.query;

import static io.reflectoring.coderadar.rest.GetCommitResponseMapper.mapCommits;

import io.reflectoring.coderadar.query.domain.ContributorsForFile;
import io.reflectoring.coderadar.query.domain.FileAndCommitsForTimePeriod;
import io.reflectoring.coderadar.query.port.driver.GetCriticalFilesUseCase;
import io.reflectoring.coderadar.query.port.driver.GetFilesWithContributorsCommand;
import io.reflectoring.coderadar.query.port.driver.GetFrequentlyChangedFilesCommand;
import io.reflectoring.coderadar.rest.domain.FileAndCommitsForTimePeriodResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Transactional
public class GetCriticalFilesController {
  private final GetCriticalFilesUseCase getCriticalFilesUseCase;

  public GetCriticalFilesController(GetCriticalFilesUseCase getCriticalFilesUseCase) {
    this.getCriticalFilesUseCase = getCriticalFilesUseCase;
  }

  @RequestMapping(
      method = {RequestMethod.POST, RequestMethod.GET},
      path = "/projects/{projectId}/files/critical",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<ContributorsForFile>> getFilesWithContributors(
      @PathVariable Long projectId,
      @RequestBody @Validated GetFilesWithContributorsCommand command) {
    return new ResponseEntity<>(
        getCriticalFilesUseCase.getFilesWithContributors(projectId, command), HttpStatus.OK);
  }

  @RequestMapping(
      method = {RequestMethod.POST, RequestMethod.GET},
      path = "/projects/{projectId}/files/modification/frequency")
  public ResponseEntity<List<FileAndCommitsForTimePeriodResponse>> getFrequentlyChangedFiles(
      @PathVariable long projectId,
      @RequestBody @Validated GetFrequentlyChangedFilesCommand command) {
    return new ResponseEntity<>(
        mapResponse(getCriticalFilesUseCase.getFrequentlyChangedFiles(projectId, command)),
        HttpStatus.OK);
  }

  private List<FileAndCommitsForTimePeriodResponse> mapResponse(
      List<FileAndCommitsForTimePeriod> files) {
    List<FileAndCommitsForTimePeriodResponse> result = new ArrayList<>(files.size());
    for (FileAndCommitsForTimePeriod f : files) {
      FileAndCommitsForTimePeriodResponse resultItem = new FileAndCommitsForTimePeriodResponse();
      resultItem.setPath(f.getPath());
      resultItem.setCommits(mapCommits(f.getCommits()));
      result.add(resultItem);
    }
    return result;
  }
}
