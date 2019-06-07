package io.reflectoring.coderadar.projectadministration.service.filepattern;

import io.reflectoring.coderadar.projectadministration.FilePatternNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.GetFilePatternPort;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.UpdateFilePatternPort;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.update.UpdateFilePatternCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.update.UpdateFilePatternUseCase;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateFilePatternService implements UpdateFilePatternUseCase {

  private final GetFilePatternPort getFilePatternPort;
  private final UpdateFilePatternPort updateFilePatternPort;

  @Autowired
  public UpdateFilePatternService(
      GetFilePatternPort getFilePatternPort, UpdateFilePatternPort updateFilePatternPort) {
    this.getFilePatternPort = getFilePatternPort;
    this.updateFilePatternPort = updateFilePatternPort;
  }

  @Override
  public void updateFilePattern(UpdateFilePatternCommand command, Long filePatternId)
      throws FilePatternNotFoundException {
    Optional<FilePattern> filePattern = getFilePatternPort.get(filePatternId);
    if (filePattern.isPresent()) {
      filePattern.get().setPattern(command.getPattern());
      filePattern.get().setInclusionType(command.getInclusionType());
      updateFilePatternPort.updateFilePattern(filePattern.get());
    } else {
      throw new FilePatternNotFoundException(filePatternId);
    }
  }
}
