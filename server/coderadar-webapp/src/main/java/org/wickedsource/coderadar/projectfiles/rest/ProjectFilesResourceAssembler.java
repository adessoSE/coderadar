package org.wickedsource.coderadar.projectfiles.rest;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.rest.ProjectController;
import org.wickedsource.coderadar.projectfiles.domain.ProjectFiles;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectFilesResourceAssembler extends ResourceAssemblerSupport<ProjectFiles, ProjectFilesResource> {

    public ProjectFilesResourceAssembler() {
        super(ProjectController.class, ProjectFilesResource.class);
    }

    @Override
    public ProjectFilesResource toResource(ProjectFiles entity) {
        ProjectFilesResource resource = new ProjectFilesResource();
        resource.setFileType(entity.getFileType());
        resource.setInclusionType(entity.getInclusionType());
        resource.setPattern(entity.getPattern());
        return resource;
    }

    public List<ProjectFilesResource> toResourceList(Iterable<ProjectFiles> entities) {
        List<ProjectFilesResource> resourceList = new ArrayList<>();
        for (ProjectFiles entity : entities) {
            resourceList.add(toResource(entity));
        }
        return resourceList;
    }

    public ProjectFiles toEntity(ProjectFilesResource resource, Project project) {
        ProjectFiles entity = new ProjectFiles();
        entity.setPattern(resource.getPattern());
        entity.setInclusionType(resource.getInclusionType());
        entity.setFileType(resource.getFileType());
        entity.setProject(project);
        return entity;
    }

    public List<ProjectFiles> toEntityList(Iterable<ProjectFilesResource> resourceList, Project project) {
        List<ProjectFiles> entities = new ArrayList<>();
        for (ProjectFilesResource resource : resourceList) {
            entities.add(toEntity(resource, project));
        }
        return entities;
    }
}
