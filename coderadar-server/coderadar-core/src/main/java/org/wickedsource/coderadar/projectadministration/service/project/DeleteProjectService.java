package org.wickedsource.coderadar.projectadministration.service.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.projectadministration.domain.Project;
import org.wickedsource.coderadar.projectadministration.port.driven.project.DeleteProjectPort;
import org.wickedsource.coderadar.projectadministration.port.driver.project.DeleteProjectCommand;
import org.wickedsource.coderadar.projectadministration.port.driver.project.DeleteProjectUseCase;

@Service
public class DeleteProjectService implements DeleteProjectUseCase {

  private final DeleteProjectPort deleteProjectPort;

  @Autowired
  public DeleteProjectService(DeleteProjectPort deleteProjectPort) {
    this.deleteProjectPort = deleteProjectPort;
  }

  @Override
  public void delete(DeleteProjectCommand command) {
    deleteProjectPort.delete(command.getId());
  }
}
