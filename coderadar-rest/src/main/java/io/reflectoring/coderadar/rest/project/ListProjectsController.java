package io.reflectoring.coderadar.rest.project;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driver.project.get.ListProjectsUseCase;
import io.reflectoring.coderadar.rest.domain.GetProjectResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class ListProjectsController {
  private final ListProjectsUseCase listProjectsUseCase;

  public ListProjectsController(ListProjectsUseCase listProjectsUseCase) {
    this.listProjectsUseCase = listProjectsUseCase;
  }

  @GetMapping(path = "/projects", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<GetProjectResponse>> listProjects() {
    List<Project> projects = listProjectsUseCase.listProjects();
    List<GetProjectResponse> responses = new ArrayList<>(projects.size());
    for (Project project : projects) {
      responses.add(
          new GetProjectResponse()
              .setName(project.getName())
              .setId(project.getId())
              .setStartDate(project.getVcsStart())
              .setEndDate(project.getVcsEnd())
              .setVcsOnline(project.isVcsOnline())
              .setVcsUrl(project.getVcsUrl())
              .setVcsUsername(project.getVcsUsername()));
    }
    return new ResponseEntity<>(responses, HttpStatus.OK);
  }
}
