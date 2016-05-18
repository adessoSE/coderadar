package org.wickedsource.coderadar.projectfiles.rest;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.projectfiles.domain.ProjectFiles;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class ProjectFilesResourceAssembler extends ResourceAssemblerSupport<Iterable<ProjectFiles>, ProjectFilesResource> {

    private Long projectId;

    ProjectFilesResourceAssembler(Long projectId) {
        super(ProjectFilesController.class, ProjectFilesResource.class);
        this.projectId = projectId;
    }

    @Override
    public ProjectFilesResource toResource(Iterable<ProjectFiles> entities) {
        ProjectFilesResource resource = new ProjectFilesResource();
        for (ProjectFiles entity : entities) {
            ProjectFilesDTO dto = new ProjectFilesDTO();
            dto.setFileType(entity.getFileType());
            dto.setInclusionType(entity.getInclusionType());
            dto.setPattern(entity.getPattern());
            resource.addProjectFiles(dto);
        }
        resource.add(linkTo(methodOn(ProjectFilesController.class).getProjectFiles(projectId)).withSelfRel());
        return resource;
    }

    List<ProjectFiles> toEntity(ProjectFilesResource resource, Project project) {
        List<ProjectFiles> entities = new ArrayList<>();
        for (ProjectFilesDTO dto : resource.getProjectFilesList()) {
            ProjectFiles entity = new ProjectFiles();
            entity.setPattern(dto.getPattern());
            entity.setInclusionType(dto.getInclusionType());
            entity.setFileType(dto.getFileType());
            entity.setProject(project);
            entities.add(entity);
        }
        return entities;
    }

}
