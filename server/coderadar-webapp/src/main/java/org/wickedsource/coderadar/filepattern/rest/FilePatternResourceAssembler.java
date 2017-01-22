package org.wickedsource.coderadar.filepattern.rest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.wickedsource.coderadar.filepattern.domain.FilePattern;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.rest.ProjectController;

public class FilePatternResourceAssembler
    extends ResourceAssemblerSupport<Iterable<FilePattern>, FilePatternResource> {

  private Long projectId;

  FilePatternResourceAssembler(Long projectId) {
    super(FilePatternController.class, FilePatternResource.class);
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
    resource.add(
        linkTo(methodOn(FilePatternController.class).getFilePatterns(projectId)).withSelfRel());
    resource.add(
        linkTo(methodOn(ProjectController.class).getProject(projectId)).withRel("project"));
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
