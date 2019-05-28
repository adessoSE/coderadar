package io.reflectoring.coderadar.graph.projectadministration.filepattern.service;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern.DeleteFilePatternPort;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.DeleteFilePatternRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("DeleteFilePatternServiceNeo4j")
public class DeleteFilePatternService implements DeleteFilePatternPort {
  private DeleteFilePatternRepository deleteFilePatternRepository;

  @Autowired
  public DeleteFilePatternService(DeleteFilePatternRepository deleteFilePatternRepository) {
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
