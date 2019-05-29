package io.reflectoring.coderadar.core.projectadministration.service.filepattern;

import io.reflectoring.coderadar.core.projectadministration.FilePatternNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern.GetFilePatternPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern.UpdateFilePatternPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.update.UpdateFilePatternCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.update.UpdateFilePatternUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("UpdateFilePatternService")
public class UpdateFilePatternService implements UpdateFilePatternUseCase {

  private final GetFilePatternPort getFilePatternPort;
  private final UpdateFilePatternPort updateFilePatternPort;

  @Autowired
  public UpdateFilePatternService(
      @Qualifier("GetFilePatternServiceNeo4j") GetFilePatternPort getFilePatternPort,
      @Qualifier("UpdateFilePatternServiceNeo4j") UpdateFilePatternPort updateFilePatternPort) {
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
