package io.reflectoring.coderadar.projectadministration.service.filepattern;

import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.GetFilePatternPort;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.UpdateFilePatternPort;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.update.UpdateFilePatternCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.update.UpdateFilePatternUseCase;
import org.springframework.stereotype.Service;

@Service
public class UpdateFilePatternService implements UpdateFilePatternUseCase {

  private final GetFilePatternPort getFilePatternPort;
  private final UpdateFilePatternPort updateFilePatternPort;

  public UpdateFilePatternService(
      GetFilePatternPort getFilePatternPort, UpdateFilePatternPort updateFilePatternPort) {
    this.getFilePatternPort = getFilePatternPort;
    this.updateFilePatternPort = updateFilePatternPort;
  }

  @Override
  public void updateFilePattern(UpdateFilePatternCommand command, Long filePatternId) {
    FilePattern filePattern = getFilePatternPort.get(filePatternId);
    filePattern.setPattern(command.getPattern());
    filePattern.setInclusionType(command.getInclusionType());
    updateFilePatternPort.updateFilePattern(filePattern);
  }
}
