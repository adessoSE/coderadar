package io.reflectoring.coderadar.graph.projectadministration.filepattern.service;

import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.GetFilePatternRepository;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.GetFilePatternPort;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetFilePatternAdapter implements GetFilePatternPort {

  private GetFilePatternRepository getFilePatternRepository;

  @Autowired
  public GetFilePatternAdapter(GetFilePatternRepository getFilePatternRepository) {
    this.getFilePatternRepository = getFilePatternRepository;
  }

  @Override
  public Optional<FilePattern> get(Long id) {
    return getFilePatternRepository.findById(id);
  }
}
