package io.reflectoring.coderadar.projectadministration.service.filepattern;

import io.reflectoring.coderadar.projectadministration.FilePatternAlreadyExistsException;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.CreateFilePatternPort;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.ListFilePatternsOfProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.create.CreateFilePatternCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.create.CreateFilePatternUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateFilePatternService implements CreateFilePatternUseCase {

  private final CreateFilePatternPort createFilePatternPort;
  private final ListFilePatternsOfProjectPort listFilePatternsOfProjectPort;
  private final GetProjectPort getProjectPort;
  private static final Logger logger = LoggerFactory.getLogger(CreateFilePatternService.class);

  @Override
  public Long createFilePattern(CreateFilePatternCommand command, long projectId) {
    if (!getProjectPort.existsById(projectId)) {
      throw new ProjectNotFoundException(projectId);
    }
    if (listFilePatternsOfProjectPort.listFilePatterns(projectId).stream()
        .anyMatch(
            filePattern ->
                filePattern.getInclusionType().equals(command.getInclusionType())
                    && filePattern.getPattern().equals(command.getPattern()))) {
      throw new FilePatternAlreadyExistsException(command.getInclusionType(), command.getPattern());
    }
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
