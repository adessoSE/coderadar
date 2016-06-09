package org.wickedsource.coderadar.filepattern.rest;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.wickedsource.coderadar.filepattern.domain.FilePattern;
import org.wickedsource.coderadar.project.domain.Project;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class FilePatternResourceAssembler extends ResourceAssemblerSupport<Iterable<FilePattern>, FilePatternResource> {

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
            dto.setFileType(entity.getFileType());
            dto.setInclusionType(entity.getInclusionType());
            dto.setPattern(entity.getPattern());
            resource.addProjectFiles(dto);
        }
        resource.add(linkTo(methodOn(FilePatternController.class).getFilePatterns(projectId)).withSelfRel());
        return resource;
    }

    List<FilePattern> toEntity(FilePatternResource resource, Project project) {
        List<FilePattern> entities = new ArrayList<>();
        for (FilePatternDTO dto : resource.getProjectFiles()) {
            FilePattern entity = new FilePattern();
            entity.setPattern(dto.getPattern());
            entity.setInclusionType(dto.getInclusionType());
            entity.setFileType(dto.getFileType());
            entity.setProject(project);
            entities.add(entity);
        }
        return entities;
    }

}
