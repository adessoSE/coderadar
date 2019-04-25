package io.reflectoring.coderadar.core.projectadministration.port.driver.project.get;

import java.util.List;

public interface ListProjectsUseCase {
  List<GetProjectResponse> listProjects();
}
