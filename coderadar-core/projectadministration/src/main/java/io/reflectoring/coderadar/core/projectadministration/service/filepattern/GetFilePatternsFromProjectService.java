package io.reflectoring.coderadar.core.projectadministration.service.filepattern;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern.GetFilePatternsFromProjectPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.GetFilePatternFromProjectUseCase;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetFilePatternsFromProjectService implements GetFilePatternFromProjectUseCase {

  private final GetFilePatternsFromProjectPort getFilePatternsFromProjectPort;

  @Autowired
  public GetFilePatternsFromProjectService(
      GetFilePatternsFromProjectPort getFilePatternsFromProjectPort) {
    this.getFilePatternsFromProjectPort = getFilePatternsFromProjectPort;
  }

  @Override
  public List<FilePattern> getFilePatterns(Long projectId) {
    return getFilePatternsFromProjectPort.getFilePatterns(projectId);
  }
}
