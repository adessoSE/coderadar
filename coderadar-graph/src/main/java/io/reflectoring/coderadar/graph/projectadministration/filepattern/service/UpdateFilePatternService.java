package io.reflectoring.coderadar.graph.projectadministration.filepattern.service;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern.UpdateFilePatternPort;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.UpdateFilePatternRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UpdateFilePatternServiceNeo4j")
public class UpdateFilePatternService implements UpdateFilePatternPort {

  private final UpdateFilePatternRepository updateFilePatternRepository;

  @Autowired
  public UpdateFilePatternService(UpdateFilePatternRepository updateFilePatternRepository) {
    this.updateFilePatternRepository = updateFilePatternRepository;
  }

  @Override
  public void updateFilePattern(FilePattern filePattern) {
    updateFilePatternRepository.save(filePattern);
  }
}
