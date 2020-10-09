package io.reflectoring.coderadar.rest.query;

import static io.reflectoring.coderadar.rest.GetCommitResponseMapper.mapCommits;

import io.reflectoring.coderadar.query.domain.ContributorsForFile;
import io.reflectoring.coderadar.query.domain.FileAndCommitsForTimePeriod;
import io.reflectoring.coderadar.query.port.driver.criticalfiles.GetCriticalFilesUseCase;
import io.reflectoring.coderadar.query.port.driver.criticalfiles.GetFilesWithContributorsCommand;
import io.reflectoring.coderadar.query.port.driver.criticalfiles.GetFrequentlyChangedFilesCommand;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.domain.FileAndCommitsForTimePeriodResponse;
import io.reflectoring.coderadar.useradministration.service.security.AuthenticationService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Transactional
@RestController
@RequiredArgsConstructor
public class GetCriticalFilesController implements AbstractBaseController {
  private final GetCriticalFilesUseCase getCriticalFilesUseCase;
  private final AuthenticationService authenticationService;

  @RequestMapping(
      method = {RequestMethod.POST, RequestMethod.GET},
      path = "/projects/{projectId}/files/critical",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<ContributorsForFile>> getFilesWithContributors(
      @PathVariable Long projectId,
      @RequestBody @Validated GetFilesWithContributorsCommand command) {
    authenticationService.authenticateMember(projectId);
    return new ResponseEntity<>(
        getCriticalFilesUseCase.getFilesWithContributors(projectId, command), HttpStatus.OK);
  }

  @RequestMapping(
      method = {RequestMethod.POST, RequestMethod.GET},
      path = "/projects/{projectId}/files/modification/frequency")
  public ResponseEntity<List<FileAndCommitsForTimePeriodResponse>> getFrequentlyChangedFiles(
      @PathVariable long projectId,
      @RequestBody @Validated GetFrequentlyChangedFilesCommand command) {
    authenticationService.authenticateMember(projectId);
    return new ResponseEntity<>(
        mapResponse(getCriticalFilesUseCase.getFrequentlyChangedFiles(projectId, command)),
        HttpStatus.OK);
  }

  private List<FileAndCommitsForTimePeriodResponse> mapResponse(
      List<FileAndCommitsForTimePeriod> files) {
    List<FileAndCommitsForTimePeriodResponse> result = new ArrayList<>(files.size());
    for (FileAndCommitsForTimePeriod f : files) {
      result.add(new FileAndCommitsForTimePeriodResponse(f.getPath(), mapCommits(f.getCommits())));
    }
    return result;
  }
}
