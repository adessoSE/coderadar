package io.reflectoring.coderadar.graph.projectadministration.filepattern.service;

import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.CreateFilePatternRepository;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.CreateFilePatternPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateFilePatternAdapter implements CreateFilePatternPort {

  private CreateFilePatternRepository createFilePatternRepository;

  @Autowired
  public CreateFilePatternAdapter(CreateFilePatternRepository createFilePatternRepository) {
    this.createFilePatternRepository = createFilePatternRepository;
  }

  @Override
  public Long createFilePattern(FilePattern filePattern) {
    return createFilePatternRepository.save(filePattern).getId();
  }
}
