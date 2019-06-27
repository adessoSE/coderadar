package io.reflectoring.coderadar.graph.projectadministration.filepattern.service;

import io.reflectoring.coderadar.graph.projectadministration.domain.FilePatternEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.FilePatternMapper;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.CreateFilePatternRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.CreateFilePatternPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateFilePatternAdapter implements CreateFilePatternPort {

  private final GetProjectRepository getProjectRepository;
  private final CreateFilePatternRepository createFilePatternRepository;
  private final FilePatternMapper filePatternMapper = new FilePatternMapper();

  @Autowired
  public CreateFilePatternAdapter(
      GetProjectRepository getProjectRepository,
      CreateFilePatternRepository createFilePatternRepository) {
    this.getProjectRepository = getProjectRepository;
    this.createFilePatternRepository = createFilePatternRepository;
  }

  @Override
  public Long createFilePattern(FilePattern filePattern, Long projectId)
      throws ProjectNotFoundException {
    FilePatternEntity filePatternEntity = filePatternMapper.mapDomainObject(filePattern);
    ProjectEntity projectEntity =
        getProjectRepository
            .findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId));
    filePatternEntity.setProject(projectEntity);
    projectEntity.getFilePatterns().add(filePatternEntity);
    getProjectRepository.save(projectEntity);
    return createFilePatternRepository.save(filePatternEntity).getId();
  }
}
