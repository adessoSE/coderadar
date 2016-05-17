package org.wickedsource.coderadar.projectfiles.rest;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.rest.ProjectController;
import org.wickedsource.coderadar.projectfiles.domain.ProjectFiles;

import java.util.ArrayList;
import java.util.List;

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
        resource.setProjectId(entity.getProject().getId());
        return resource;
    }

    public List<ProjectFilesResource> toResourceList(List<ProjectFiles> entities){
        List<ProjectFilesResource> resourceList = new ArrayList<>();
        for(ProjectFiles entity : entities){
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
}
