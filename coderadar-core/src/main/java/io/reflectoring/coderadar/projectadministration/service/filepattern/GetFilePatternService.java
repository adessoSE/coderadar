package io.reflectoring.coderadar.projectadministration.service.filepattern;

import io.reflectoring.coderadar.projectadministration.FilePatternNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.GetFilePatternPort;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.get.GetFilePatternResponse;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.get.GetFilePatternUseCase;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("GetFilePatternService")
public class GetFilePatternService implements GetFilePatternUseCase {
  private final GetFilePatternPort getFilePatternPort;

  @Autowired
  public GetFilePatternService(
      @Qualifier("GetFilePatternServiceNeo4j") GetFilePatternPort getFilePatternPort) {
    this.getFilePatternPort = getFilePatternPort;
  }

  @Override
  public GetFilePatternResponse get(Long id) throws FilePatternNotFoundException {
    Optional<FilePattern> filePattern = getFilePatternPort.get(id);
    if (filePattern.isPresent()) {
      return new GetFilePatternResponse(
          id, filePattern.get().getPattern(), filePattern.get().getInclusionType());
    } else {
      throw new FilePatternNotFoundException(id);
    }
  }
}
