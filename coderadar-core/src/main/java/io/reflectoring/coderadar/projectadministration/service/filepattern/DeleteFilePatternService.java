package io.reflectoring.coderadar.projectadministration.service.filepattern;

import io.reflectoring.coderadar.projectadministration.FilePatternNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.DeleteFilePatternPort;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.delete.DeleteFilePatternFromProjectUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteFilePatternService implements DeleteFilePatternFromProjectUseCase {

  private final DeleteFilePatternPort deleteFilePatternPort;

  @Autowired
  public DeleteFilePatternService(DeleteFilePatternPort deleteFilePatternPort) {
    this.deleteFilePatternPort = deleteFilePatternPort;
  }

  @Override
  public void delete(Long id) throws FilePatternNotFoundException {
    deleteFilePatternPort.delete(id);
  }
}
