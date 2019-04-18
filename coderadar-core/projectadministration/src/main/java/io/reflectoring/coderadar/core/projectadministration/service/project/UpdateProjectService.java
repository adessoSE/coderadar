package io.reflectoring.coderadar.core.projectadministration.service.project;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.UpdateProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.UpdateProjectCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.project.UpdateProjectUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateProjectService implements UpdateProjectUseCase {

  private final GetProjectPort getProjectPort;
  private final UpdateProjectPort updateProjectPort;

  @Autowired
  public UpdateProjectService(GetProjectPort getProjectPort, UpdateProjectPort updateProjectPort) {
    this.getProjectPort = getProjectPort;
    this.updateProjectPort = updateProjectPort;
  }

  @Override
  public void update(UpdateProjectCommand command) {
    Project project = getProjectPort.get(command.getId());
    project.setName(command.getName());
    project.setWorkdirName(command.getWorkdir());
    project.setVcsUrl(command.getVcsUrl());
    project.setVcsUsername(command.getVcsUsername());
    project.setVcsPassword(command.getVcsPassword());
    project.setVcsOnline(command.getVcsOnline());
    project.setVcsStart(command.getStart());
    project.setVcsEnd(command.getEnd());
    updateProjectPort.update(project);
  }
}
