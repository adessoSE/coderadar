package io.reflectoring.coderadar.graph.projectadministration.filepattern.adapter;

import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.FilePatternRepository;
import io.reflectoring.coderadar.projectadministration.FilePatternNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.DeleteFilePatternPort;
import org.springframework.stereotype.Service;

@Service
public class DeleteFilePatternAdapter implements DeleteFilePatternPort {
  private final FilePatternRepository filePatternRepository;

  public DeleteFilePatternAdapter(FilePatternRepository filePatternRepository) {
    this.filePatternRepository = filePatternRepository;
  }

  @Override
  public void delete(Long id) {
    if (!filePatternRepository.existsById(id)) {
      throw new FilePatternNotFoundException(id);
    }
    filePatternRepository.deleteById(id);
  }
}
