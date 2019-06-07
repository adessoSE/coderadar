package io.reflectoring.coderadar.projectadministration.service.filepattern;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.CreateFilePatternPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.create.CreateFilePatternCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.create.CreateFilePatternUseCase;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateFilePatternService implements CreateFilePatternUseCase {

  private final GetProjectPort getProjectPort;
  private final CreateFilePatternPort createFilePatternPort;

  @Autowired
  public CreateFilePatternService(
      GetProjectPort getProjectPort, CreateFilePatternPort createFilePatternPort) {
    this.getProjectPort = getProjectPort;
    this.createFilePatternPort = createFilePatternPort;
  }

  @Override
  public Long createFilePattern(CreateFilePatternCommand command, Long projectId)
      throws ProjectNotFoundException {
    Optional<Project> project = getProjectPort.get(projectId);
    if (project.isPresent()) {
      FilePattern filePattern = new FilePattern();
      filePattern.setProject(project.get());
      filePattern.setPattern(command.getPattern());
      filePattern.setInclusionType(command.getInclusionType());
      return createFilePatternPort.createFilePattern(filePattern);
    } else {
      throw new ProjectNotFoundException(projectId);
    }
  }
}
