package io.reflectoring.coderadar.graph.projectadministration.filepattern.adapter;

import io.reflectoring.coderadar.graph.projectadministration.filepattern.FilePatternMapper;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.FilePatternRepository;
import io.reflectoring.coderadar.projectadministration.FilePatternNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.GetFilePatternPort;
import org.springframework.stereotype.Service;

@Service
public class GetFilePatternAdapter implements GetFilePatternPort {

  private final FilePatternRepository filePatternRepository;
  private final FilePatternMapper filePatternMapper = new FilePatternMapper();

  public GetFilePatternAdapter(FilePatternRepository filePatternRepository) {
    this.filePatternRepository = filePatternRepository;
  }

  @Override
  public FilePattern get(long id) {
    return filePatternMapper.mapGraphObject(
        filePatternRepository
            .findById(id, 0)
            .orElseThrow(() -> new FilePatternNotFoundException(id)));
  }
}
