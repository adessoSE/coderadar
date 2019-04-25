package io.reflectoring.coderadar.core.projectadministration.service.filepattern;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern.CreateFilePatternPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.create.CreateFilePatternCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.create.CreateFilePatternUseCase;
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
  public Long createFilePattern(CreateFilePatternCommand command, Long projectId) {
    FilePattern filePattern = new FilePattern();
    filePattern.setProject(getProjectPort.get(projectId));
    filePattern.setPattern(command.getPattern());
    filePattern.setInclusionType(command.getInclusionType());
    filePattern = createFilePatternPort.createFilePattern(filePattern);
    return filePattern.getId();
  }
}
