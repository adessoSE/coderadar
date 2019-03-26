package org.wickedsource.coderadar.projectadministration.port.driver.project;

import java.util.List;
import org.wickedsource.coderadar.projectadministration.domain.Project;

public interface ListProjectsUseCase {
  List<Project> listProjects();
}
