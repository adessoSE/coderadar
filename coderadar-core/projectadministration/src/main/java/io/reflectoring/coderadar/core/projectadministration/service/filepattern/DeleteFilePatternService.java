package io.reflectoring.coderadar.core.projectadministration.service.filepattern;

import io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern.DeleteFilePatternPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.delete.DeleteFilePatternFromProjectUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("DeleteFilePatternService")
public class DeleteFilePatternService implements DeleteFilePatternFromProjectUseCase {

  private final DeleteFilePatternPort deleteFilePatternPort;

  @Autowired
  public DeleteFilePatternService(@Qualifier("DeleteFilePatternServiceNeo4j") DeleteFilePatternPort deleteFilePatternPort) {
    this.deleteFilePatternPort = deleteFilePatternPort;
  }

  @Override
  public void delete(Long id) {
    deleteFilePatternPort.delete(id);
  }
}
