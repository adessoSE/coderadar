package io.reflectoring.coderadar.graph.projectadministration.filepattern.adapter;

import io.reflectoring.coderadar.graph.projectadministration.domain.FilePatternEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.FilePatternMapper;
import io.reflectoring.coderadar.graph.projectadministration.filepattern.repository.FilePatternRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.CreateFilePatternPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateFilePatternAdapter implements CreateFilePatternPort {

  private final ProjectRepository projectRepository;
  private final FilePatternRepository filePatternRepository;

  private final FilePatternMapper filePatternMapper = new FilePatternMapper();

  @Override
  public Long createFilePattern(FilePattern filePattern, long projectId) {
    FilePatternEntity filePatternEntity = filePatternMapper.mapDomainObject(filePattern);
    ProjectEntity projectEntity =
        projectRepository
            .findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId));
    projectEntity.getFilePatterns().add(filePatternEntity);
    projectRepository.save(projectEntity);
    return filePatternRepository.save(filePatternEntity).getId();
  }
}
