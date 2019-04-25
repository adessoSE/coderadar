package io.reflectoring.coderadar.core.projectadministration.service.filepattern;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern.GetFilePatternPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern.UpdateFilePatternPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.update.UpdateFilePatternCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.update.UpdateFilePatternForProjectUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateFilePatternService implements UpdateFilePatternForProjectUseCase {

  private final GetFilePatternPort getFilePatternPort;
  private final UpdateFilePatternPort updateFilePatternPort;

  @Autowired
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
