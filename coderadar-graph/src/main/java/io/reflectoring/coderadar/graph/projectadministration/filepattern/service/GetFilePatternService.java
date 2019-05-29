package io.reflectoring.coderadar.graph.projectadministration.filepattern.service;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern.GetFilePatternPort;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.GetFilePatternRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("GetFilePatternServiceNeo4j")
public class GetFilePatternService implements GetFilePatternPort {

  private GetFilePatternRepository getFilePatternRepository;

  @Autowired
  public GetFilePatternService(GetFilePatternRepository getFilePatternRepository) {
    this.getFilePatternRepository = getFilePatternRepository;
  }

  @Override
  public Optional<FilePattern> get(Long id) {
    return getFilePatternRepository.findById(id);
  }
}
