package io.reflectoring.coderadar.graph.projectadministration.filepattern.adapter;

import io.reflectoring.coderadar.domain.FilePattern;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.FilePatternMapper;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.FilePatternRepository;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.ListFilePatternsOfProjectPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListFilePatternsOfProjectAdapter implements ListFilePatternsOfProjectPort {
  private final FilePatternRepository filePatternRepository;
  private final FilePatternMapper filePatternMapper = new FilePatternMapper();

  @Override
  public List<FilePattern> listFilePatterns(long projectId) {
    return filePatternMapper.mapNodeEntities(filePatternRepository.findByProjectId(projectId));
  }
}
