package io.reflectoring.coderadar.graph.projectadministration.filepattern.service;

import io.reflectoring.coderadar.graph.projectadministration.filepattern.FilePatternMapper;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.GetFilePatternRepository;
import io.reflectoring.coderadar.projectadministration.FilePatternNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.GetFilePatternPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetFilePatternAdapter implements GetFilePatternPort {

  private final GetFilePatternRepository getFilePatternRepository;
  private final FilePatternMapper filePatternMapper = new FilePatternMapper();

  @Autowired
  public GetFilePatternAdapter(GetFilePatternRepository getFilePatternRepository) {
    this.getFilePatternRepository = getFilePatternRepository;
  }

  @Override
  public FilePattern get(Long id) throws FilePatternNotFoundException {
    return filePatternMapper.mapNodeEntity(
        getFilePatternRepository
            .findById(id)
            .orElseThrow(() -> new FilePatternNotFoundException(id)));
  }
}
