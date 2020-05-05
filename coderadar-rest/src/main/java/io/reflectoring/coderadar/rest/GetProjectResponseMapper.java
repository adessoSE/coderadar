package io.reflectoring.coderadar.rest;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.rest.domain.GetProjectResponse;
import java.util.ArrayList;
import java.util.List;

public class GetProjectResponseMapper {
  private GetProjectResponseMapper() {}

  public static GetProjectResponse mapProject(Project project) {
    return new GetProjectResponse()
        .setName(project.getName())
        .setId(project.getId())
        .setStartDate(project.getVcsStart())
        .setVcsOnline(project.isVcsOnline())
        .setVcsUrl(project.getVcsUrl())
        .setVcsUsername(project.getVcsUsername());
  }

  public static List<GetProjectResponse> mapProjects(List<Project> projects) {
    List<GetProjectResponse> responses = new ArrayList<>(projects.size());
    for (Project project : projects) {
      responses.add(
          new GetProjectResponse()
              .setName(project.getName())
              .setId(project.getId())
              .setStartDate(project.getVcsStart())
              .setVcsOnline(project.isVcsOnline())
              .setVcsUrl(project.getVcsUrl())
              .setVcsUsername(project.getVcsUsername()));
    }
    return responses;
  }
}
