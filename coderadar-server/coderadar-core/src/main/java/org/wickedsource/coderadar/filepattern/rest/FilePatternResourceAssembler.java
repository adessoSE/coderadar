package org.wickedsource.coderadar.filepattern.rest;

import java.util.ArrayList;
import java.util.List;
import org.wickedsource.coderadar.core.rest.AbstractResourceAssembler;
import org.wickedsource.coderadar.projectadministration.domain.FilePattern;
import org.wickedsource.coderadar.projectadministration.domain.Project;

public class FilePatternResourceAssembler
    extends AbstractResourceAssembler<Iterable<FilePattern>, FilePatternResource> {

  private Long projectId;

  FilePatternResourceAssembler(Long projectId) {
    this.projectId = projectId;
  }

  @Override
  public FilePatternResource toResource(Iterable<FilePattern> entities) {
    FilePatternResource resource = new FilePatternResource();
    for (FilePattern entity : entities) {
      FilePatternDTO dto = new FilePatternDTO();
      dto.setFileSetType(entity.getFileSetType());
      dto.setInclusionType(entity.getInclusionType());
      dto.setPattern(entity.getPattern());
      resource.addFilePattern(dto);
    }
    return resource;
  }

  List<FilePattern> toEntity(FilePatternResource resource, Project project) {
    List<FilePattern> entities = new ArrayList<>();
    for (FilePatternDTO dto : resource.getFilePatterns()) {
      FilePattern entity = new FilePattern();
      entity.setPattern(dto.getPattern());
      entity.setInclusionType(dto.getInclusionType());
      entity.setFileSetType(dto.getFileSetType());
      entity.setProject(project);
      entities.add(entity);
    }
    return entities;
  }
}
