package io.reflectoring.coderadar.graph.projectadministration.filepattern.service;

import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.ListFilePatternsOfProjectRepository;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.ListFilePatternsOfProjectPort;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListFilePatternsOfProjectAdapter implements ListFilePatternsOfProjectPort {
  private ListFilePatternsOfProjectRepository listFilePatternsOfProjectRepository;

  @Autowired
  public ListFilePatternsOfProjectAdapter(
      ListFilePatternsOfProjectRepository listFilePatternsOfProjectRepository) {
    this.listFilePatternsOfProjectRepository = listFilePatternsOfProjectRepository;
  }

  @Override
  public List<FilePattern> listFilePatterns(Long projectId) {
    return listFilePatternsOfProjectRepository.findByProjectId(projectId);
  }
}
