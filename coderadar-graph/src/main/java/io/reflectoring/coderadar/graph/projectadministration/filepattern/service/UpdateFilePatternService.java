package io.reflectoring.coderadar.graph.projectadministration.filepattern.service;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern.UpdateFilePatternPort;
import org.springframework.stereotype.Service;

@Service("UpdateFilePatternServiceNeo4j")
public class UpdateFilePatternService implements UpdateFilePatternPort {
  @Override
  public void updateFilePattern(FilePattern filePattern) {}
}
