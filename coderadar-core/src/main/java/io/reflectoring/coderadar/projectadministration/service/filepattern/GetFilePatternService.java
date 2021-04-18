package io.reflectoring.coderadar.projectadministration.service.filepattern;

import io.reflectoring.coderadar.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.GetFilePatternPort;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.get.GetFilePatternUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetFilePatternService implements GetFilePatternUseCase {
  private final GetFilePatternPort getFilePatternPort;

  @Override
  public FilePattern get(long id) {
    return getFilePatternPort.get(id);
  }
}
