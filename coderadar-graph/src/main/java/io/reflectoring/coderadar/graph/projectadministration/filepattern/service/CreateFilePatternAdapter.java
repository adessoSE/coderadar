package io.reflectoring.coderadar.graph.projectadministration.filepattern.service;

import io.reflectoring.coderadar.graph.projectadministration.domain.FilePatternEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.FilePatternMapper;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.FilePatternRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.CreateFilePatternPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateFilePatternAdapter implements CreateFilePatternPort {

  private final ProjectRepository projectRepository;
  private final FilePatternRepository filePatternRepository;
  private final FilePatternMapper filePatternMapper = new FilePatternMapper();

  @Autowired
  public CreateFilePatternAdapter(
      ProjectRepository projectRepository, FilePatternRepository filePatternRepository) {
    this.projectRepository = projectRepository;
    this.filePatternRepository = filePatternRepository;
  }

  @Override
  public Long createFilePattern(FilePattern filePattern, Long projectId)
      throws ProjectNotFoundException {
    FilePatternEntity filePatternEntity = filePatternMapper.mapDomainObject(filePattern);
    ProjectEntity projectEntity =
        projectRepository
            .findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId));
    filePatternEntity.setProject(projectEntity);
    projectEntity.getFilePatterns().add(filePatternEntity);
    projectRepository.save(projectEntity);
    return filePatternRepository.save(filePatternEntity).getId();
  }
}
