package io.reflectoring.coderadar.projectadministration.port.driver.project.get;

import java.util.List;

public interface ListProjectsUseCase {
  List<GetProjectResponse> listProjects();
}
