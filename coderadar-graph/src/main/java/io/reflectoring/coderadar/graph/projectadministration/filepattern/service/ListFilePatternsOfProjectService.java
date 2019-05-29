package io.reflectoring.coderadar.graph.projectadministration.filepattern.service;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern.ListFilePatternsOfProjectPort;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.ListFilePatternsOfProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ListFilePatternsOfProjectServiceNeo4j")
public class ListFilePatternsOfProjectService implements ListFilePatternsOfProjectPort {
  private ListFilePatternsOfProjectRepository listFilePatternsOfProjectRepository;

  @Autowired
  public ListFilePatternsOfProjectService(
      ListFilePatternsOfProjectRepository listFilePatternsOfProjectRepository) {
    this.listFilePatternsOfProjectRepository = listFilePatternsOfProjectRepository;
  }

  @Override
  public List<FilePattern> listFilePatterns(Long projectId) {
    return listFilePatternsOfProjectRepository.findByProjectId(projectId);
  }
}
