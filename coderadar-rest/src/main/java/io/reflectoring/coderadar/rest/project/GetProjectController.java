package io.reflectoring.coderadar.rest.project;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driver.project.get.GetProjectUseCase;
import io.reflectoring.coderadar.rest.domain.GetProjectResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class GetProjectController {
  private final GetProjectUseCase getProjectUseCase;

  public GetProjectController(GetProjectUseCase getProjectUseCase) {
    this.getProjectUseCase = getProjectUseCase;
  }

  @GetMapping(path = "/projects/{projectId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<GetProjectResponse> getProject(@PathVariable long projectId) {
    Project project = getProjectUseCase.get(projectId);
    GetProjectResponse getProjectResponse = new GetProjectResponse();
    getProjectResponse.setName(project.getName());
    getProjectResponse.setId(project.getId());
    getProjectResponse.setVcsUsername(project.getVcsUsername());
    getProjectResponse.setStartDate(project.getVcsStart());
    getProjectResponse.setVcsUrl(project.getVcsUrl());
    getProjectResponse.setVcsOnline(project.isVcsOnline());
    return new ResponseEntity<>(getProjectResponse, HttpStatus.OK);
  }
}
