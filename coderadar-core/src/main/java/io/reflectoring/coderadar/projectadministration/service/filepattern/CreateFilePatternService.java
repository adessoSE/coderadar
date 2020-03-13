package io.reflectoring.coderadar.projectadministration.service.filepattern;

import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.CreateFilePatternPort;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.create.CreateFilePatternCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.create.CreateFilePatternUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CreateFilePatternService implements CreateFilePatternUseCase {

  private final CreateFilePatternPort createFilePatternPort;
  private final Logger logger = LoggerFactory.getLogger(CreateFilePatternService.class);

  public CreateFilePatternService(CreateFilePatternPort createFilePatternPort) {
    this.createFilePatternPort = createFilePatternPort;
  }

  @Override
  public Long createFilePattern(CreateFilePatternCommand command, long projectId) {
    FilePattern filePattern = new FilePattern();
    filePattern.setPattern(command.getPattern());
    filePattern.setInclusionType(command.getInclusionType());
    Long id = createFilePatternPort.createFilePattern(filePattern, projectId);
    logger.info(
        "Set filePattern {} with type {} for project with id {}",
        filePattern.getPattern(),
        filePattern.getInclusionType(),
        projectId);
    return id;
  }
}
