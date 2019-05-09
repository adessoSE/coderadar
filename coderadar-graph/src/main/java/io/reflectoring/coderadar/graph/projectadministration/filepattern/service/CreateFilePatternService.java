package io.reflectoring.coderadar.graph.projectadministration.filepattern.service;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern.CreateFilePatternPort;
import org.springframework.stereotype.Service;

@Service("CreateFilePatternServiceNeo4j")
public class CreateFilePatternService implements CreateFilePatternPort {
  @Override
  public Long createFilePattern(FilePattern filePattern) {
    return 100L;
  }
}
