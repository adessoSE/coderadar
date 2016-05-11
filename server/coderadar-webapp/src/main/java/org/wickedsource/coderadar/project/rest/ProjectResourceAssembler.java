package org.wickedsource.coderadar.project.rest;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.VcsCoordinates;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectResourceAssembler extends ResourceAssemblerSupport<Project, ProjectResource> {

    public ProjectResourceAssembler() {
        super(ProjectController.class, ProjectResource.class);
    }

    @Override
    public ProjectResource toResource(Project project) {
        ProjectResource resource = createResourceWithId(project.getId(), project);
        resource.setName(project.getName());
        if (project.getVcsCoordinates() != null) {
            resource.setVcsType(project.getVcsCoordinates().getType());
            resource.setVcsUrl(project.getVcsCoordinates().getUrl().toString());
            resource.setVcsUser(project.getVcsCoordinates().getUsername());
            resource.setVcsPassword(project.getVcsCoordinates().getPassword());
        }

        return resource;
    }

    public List<ProjectResource> toResourceList(Iterable<Project> projects){
        List<ProjectResource> resourceList = new ArrayList<>();
        for(Project project : projects){
            resourceList.add(toResource(project));
        }
        return resourceList;
    }

    public Project toEntity(ProjectResource resource) {
        try {
            Project project = new Project();
            project.setName(resource.getName());
            VcsCoordinates vcs = new VcsCoordinates(new URL(resource.getVcsUrl()), resource.getVcsType());
            vcs.setUsername(resource.getVcsUser());
            vcs.setPassword(resource.getVcsPassword());
            project.setVcsCoordinates(vcs);
            return project;
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Invalid URL should have been caught earlier!", e);
        }
    }


}
