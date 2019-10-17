package io.reflectoring.coderadar.graph.projectadministration.filepattern.service;

import io.reflectoring.coderadar.graph.projectadministration.domain.FilePatternEntity;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.FilePatternMapper;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.FilePatternRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.ListFilePatternsOfProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driver.filepattern.get.GetFilePatternResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListFilePatternsOfProjectAdapter implements ListFilePatternsOfProjectPort {
  private final FilePatternRepository filePatternRepository;
  private final FilePatternMapper filePatternMapper = new FilePatternMapper();
  private final ProjectRepository projectRepository;

  @Autowired
  public ListFilePatternsOfProjectAdapter(
      FilePatternRepository filePatternRepository, ProjectRepository projectRepository) {
    this.filePatternRepository = filePatternRepository;
    this.projectRepository = projectRepository;
  }

  @Override
  public List<FilePattern> listFilePatterns(Long projectId) throws ProjectNotFoundException {
    projectRepository
        .findById(projectId)
        .orElseThrow(() -> new ProjectNotFoundException(projectId));
    return new ArrayList<>(
        filePatternMapper.mapNodeEntities(filePatternRepository.findByProjectId(projectId)));
  }

  @Override
  public List<GetFilePatternResponse> listFilePatternResponses(Long projectId) {
    projectRepository
        .findById(projectId)
        .orElseThrow(() -> new ProjectNotFoundException(projectId));

    List<GetFilePatternResponse> getFilePatternResponses = new ArrayList<>();
    for (FilePatternEntity f : filePatternRepository.findByProjectId(projectId)) {
      GetFilePatternResponse response = new GetFilePatternResponse();
      response.setId(f.getId());
      response.setInclusionType(f.getInclusionType());
      response.setPattern(f.getPattern());
      getFilePatternResponses.add(response);
    }
    return getFilePatternResponses;
  }
}
