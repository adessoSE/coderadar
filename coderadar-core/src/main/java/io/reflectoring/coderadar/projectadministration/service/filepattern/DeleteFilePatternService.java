package io.reflectoring.coderadar.projectadministration.service.filepattern;

import io.reflectoring.coderadar.projectadministration.FilePatternNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.DeleteFilePatternPort;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.GetFilePatternPort;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.delete.DeleteFilePatternFromProjectUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteFilePatternService implements DeleteFilePatternFromProjectUseCase {

  private final DeleteFilePatternPort deleteFilePatternPort;
  private final GetFilePatternPort getFilePatternPort;
  private final Logger logger = LoggerFactory.getLogger(DeleteFilePatternService.class);

  @Autowired
  public DeleteFilePatternService(
      DeleteFilePatternPort deleteFilePatternPort, GetFilePatternPort getFilePatternPort) {
    this.deleteFilePatternPort = deleteFilePatternPort;
    this.getFilePatternPort = getFilePatternPort;
  }

  @Override
  public void delete(Long id, Long projectId) throws FilePatternNotFoundException {
    FilePattern filePattern = getFilePatternPort.get(id);
    deleteFilePatternPort.delete(id);
    logger.info(
        String.format(
            "Removed filePattern %s with type %s for project with id %d",
            filePattern.getPattern(), filePattern.getInclusionType().toString(), projectId));
  }
}
