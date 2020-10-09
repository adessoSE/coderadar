package io.reflectoring.coderadar.rest;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.rest.domain.GetProjectResponse;
import java.util.ArrayList;
import java.util.List;

public class GetProjectResponseMapper {
  private GetProjectResponseMapper() {}

  public static GetProjectResponse mapProject(Project project) {
    return new GetProjectResponse(
        project.getId(),
        project.getName(),
        project.getVcsUsername(),
        null,
        project.getVcsUrl(),
        project.getVcsStart(),
        project.getDefaultBranch());
  }

  public static List<GetProjectResponse> mapProjects(List<Project> projects) {
    List<GetProjectResponse> responses = new ArrayList<>(projects.size());
    for (Project project : projects) {
      responses.add(mapProject(project));
    }
    return responses;
  }
}
