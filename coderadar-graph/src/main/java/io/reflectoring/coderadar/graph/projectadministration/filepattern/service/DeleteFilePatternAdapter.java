package io.reflectoring.coderadar.graph.projectadministration.filepattern.service;

import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.FilePatternRepository;
import io.reflectoring.coderadar.projectadministration.FilePatternNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.DeleteFilePatternPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteFilePatternAdapter implements DeleteFilePatternPort {
  private final FilePatternRepository filePatternRepository;

  @Autowired
  public DeleteFilePatternAdapter(FilePatternRepository filePatternRepository) {
    this.filePatternRepository = filePatternRepository;
  }

  @Override
  public void delete(Long id) throws FilePatternNotFoundException {
    filePatternRepository.findById(id).orElseThrow(() -> new FilePatternNotFoundException(id));
    filePatternRepository.deleteById(id);
  }

  @Override
  public void delete(FilePattern filePattern) throws FilePatternNotFoundException {
    filePatternRepository
        .findById(filePattern.getId())
        .orElseThrow(() -> new FilePatternNotFoundException(filePattern.getId()));
    filePatternRepository.deleteById(filePattern.getId());
  }
}
