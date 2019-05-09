package io.reflectoring.coderadar.graph.projectadministration.filepattern.service;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern.GetFilePatternPort;
import org.springframework.stereotype.Service;

@Service("GetFilePatternServiceNeo4j")
public class GetFilePatternService implements GetFilePatternPort {
  @Override
  public FilePattern get(Long id) {
    return new FilePattern();
  }
}
