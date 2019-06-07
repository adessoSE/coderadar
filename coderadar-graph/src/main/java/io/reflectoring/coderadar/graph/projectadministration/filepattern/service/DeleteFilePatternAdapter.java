package io.reflectoring.coderadar.graph.projectadministration.filepattern.service;

import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.DeleteFilePatternRepository;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.DeleteFilePatternPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteFilePatternAdapter implements DeleteFilePatternPort {
  private DeleteFilePatternRepository deleteFilePatternRepository;

  @Autowired
  public DeleteFilePatternAdapter(DeleteFilePatternRepository deleteFilePatternRepository) {
    this.deleteFilePatternRepository = deleteFilePatternRepository;
  }

  @Override
  public void delete(Long id) {
    deleteFilePatternRepository.deleteById(id);
  }

  @Override
  public void delete(FilePattern filePattern) {
    deleteFilePatternRepository.delete(filePattern);
  }
}
