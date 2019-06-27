package io.reflectoring.coderadar.graph.projectadministration.filepattern.service;

import io.reflectoring.coderadar.graph.projectadministration.domain.FilePatternEntity;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.UpdateFilePatternRepository;
import io.reflectoring.coderadar.projectadministration.FilePatternNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.UpdateFilePatternPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateFilePatternAdapter implements UpdateFilePatternPort {

  private final UpdateFilePatternRepository updateFilePatternRepository;

  @Autowired
  public UpdateFilePatternAdapter(UpdateFilePatternRepository updateFilePatternRepository) {
    this.updateFilePatternRepository = updateFilePatternRepository;
  }

  @Override
  public void updateFilePattern(FilePattern filePattern) throws FilePatternNotFoundException {
    FilePatternEntity filePatternEntity =
        updateFilePatternRepository
            .findById(filePattern.getId())
            .orElseThrow(() -> new FilePatternNotFoundException(filePattern.getId()));
    filePatternEntity.setInclusionType(filePattern.getInclusionType());
    filePatternEntity.setPattern(filePattern.getPattern());
    updateFilePatternRepository.save(filePatternEntity);
  }
}
