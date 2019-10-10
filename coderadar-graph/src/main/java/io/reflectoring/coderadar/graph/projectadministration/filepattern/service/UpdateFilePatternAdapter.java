package io.reflectoring.coderadar.graph.projectadministration.filepattern.service;

import io.reflectoring.coderadar.graph.projectadministration.domain.FilePatternEntity;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.FilePatternRepository;
import io.reflectoring.coderadar.projectadministration.FilePatternNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.UpdateFilePatternPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateFilePatternAdapter implements UpdateFilePatternPort {

  private final FilePatternRepository filePatternRepository;

  @Autowired
  public UpdateFilePatternAdapter(FilePatternRepository filePatternRepository) {
    this.filePatternRepository = filePatternRepository;
  }

  @Override
  public void updateFilePattern(FilePattern filePattern) throws FilePatternNotFoundException {
    FilePatternEntity filePatternEntity =
        filePatternRepository
            .findById(filePattern.getId())
            .orElseThrow(() -> new FilePatternNotFoundException(filePattern.getId()));
    filePatternEntity.setInclusionType(filePattern.getInclusionType());
    filePatternEntity.setPattern(filePattern.getPattern());
    filePatternRepository.save(filePatternEntity);
  }
}
