package io.reflectoring.coderadar.rest.project;

import io.reflectoring.coderadar.projectadministration.port.driver.project.get.ListProjectsUseCase;
import io.reflectoring.coderadar.rest.domain.GetProjectResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Transactional
public class ListProjectsController {
  private final ListProjectsUseCase listProjectsUseCase;

  public ListProjectsController(ListProjectsUseCase listProjectsUseCase) {
    this.listProjectsUseCase = listProjectsUseCase;
  }

  @GetMapping(path = "/projects", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<GetProjectResponse>> listProjects() {
    return new ResponseEntity<>(listProjectsUseCase.listProjects().stream().map(project ->
            new GetProjectResponse()
                    .setName(project.getName())
                    .setId(project.getId())
                    .setStartDate(project.getVcsStart())
                    .setEndDate(project.getVcsEnd())
                    .setVcsOnline(project.isVcsOnline())
                    .setVcsUrl(project.getVcsUrl())
                    .setVcsUsername(project.getVcsUsername()))
            .collect(Collectors.toList()), HttpStatus.OK);
  }
}
