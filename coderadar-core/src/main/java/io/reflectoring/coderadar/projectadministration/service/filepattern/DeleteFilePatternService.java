package io.reflectoring.coderadar.projectadministration.service.filepattern;

import io.reflectoring.coderadar.projectadministration.FilePatternNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.DeleteFilePatternPort;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.GetFilePatternPort;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.delete.DeleteFilePatternFromProjectUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteFilePatternService implements DeleteFilePatternFromProjectUseCase {

  private final DeleteFilePatternPort deleteFilePatternPort;
  private final GetFilePatternPort getFilePatternPort;

  @Autowired
  public DeleteFilePatternService(
      DeleteFilePatternPort deleteFilePatternPort, GetFilePatternPort getFilePatternPort) {
    this.deleteFilePatternPort = deleteFilePatternPort;
    this.getFilePatternPort = getFilePatternPort;
  }

  @Override
  public void delete(Long id) {
    if (getFilePatternPort.get(id).isPresent()) {
      deleteFilePatternPort.delete(id);
    } else {
      throw new FilePatternNotFoundException(id);
    }
  }
}
