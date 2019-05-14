package io.reflectoring.coderadar.graph.projectadministration.filepattern.service;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern.DeleteFilePatternPort;
import org.springframework.stereotype.Service;

@Service("DeleteFilePatternServiceNeo4j")
public class DeleteFilePatternService implements DeleteFilePatternPort {
  @Override
  public void delete(Long id) {}

  @Override
  public void delete(FilePattern filePattern) {}
}
