package io.reflectoring.coderadar.projectadministration.service.filepattern;

import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.GetFilePatternPort;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.get.GetFilePatternResponse;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.get.GetFilePatternUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetFilePatternService implements GetFilePatternUseCase {
  private final GetFilePatternPort getFilePatternPort;

  @Autowired
  public GetFilePatternService(GetFilePatternPort getFilePatternPort) {
    this.getFilePatternPort = getFilePatternPort;
  }

  @Override
  public GetFilePatternResponse get(Long id) {
    FilePattern filePattern = getFilePatternPort.get(id);
    return new GetFilePatternResponse(id, filePattern.getPattern(), filePattern.getInclusionType());
  }
}
