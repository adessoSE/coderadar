package io.reflectoring.coderadar.projectadministration.service.filepattern;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.CreateFilePatternPort;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.create.CreateFilePatternCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.create.CreateFilePatternUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateFilePatternService implements CreateFilePatternUseCase {

  private final CreateFilePatternPort createFilePatternPort;

  @Autowired
  public CreateFilePatternService(CreateFilePatternPort createFilePatternPort) {
    this.createFilePatternPort = createFilePatternPort;
  }

  @Override
  public Long createFilePattern(CreateFilePatternCommand command, Long projectId)
      throws ProjectNotFoundException {
    FilePattern filePattern = new FilePattern();
    filePattern.setPattern(command.getPattern());
    filePattern.setInclusionType(command.getInclusionType());
    return createFilePatternPort.createFilePattern(filePattern, projectId);
  }
}
