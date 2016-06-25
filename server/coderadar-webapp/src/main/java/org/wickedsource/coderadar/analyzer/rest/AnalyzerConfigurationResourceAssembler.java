package org.wickedsource.coderadar.analyzer.rest;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.wickedsource.coderadar.analyzer.domain.AnalyzerConfiguration;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.rest.ProjectController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class AnalyzerConfigurationResourceAssembler extends ResourceAssemblerSupport<AnalyzerConfiguration, AnalyzerConfigurationResource> {

    private Long projectId;

    AnalyzerConfigurationResourceAssembler(Long projectId) {
        super(AnalyzerConfigurationController.class, AnalyzerConfigurationResource.class);
        this.projectId = projectId;
    }

    @Override
    public AnalyzerConfigurationResource toResource(AnalyzerConfiguration entity) {
        AnalyzerConfigurationResource resource = new AnalyzerConfigurationResource(entity.getAnalyzerName(), entity.getEnabled());
        resource.add(linkTo(methodOn(ProjectController.class).getProject(projectId)).withRel("project"));
        resource.add(linkTo(methodOn(AnalyzerConfigurationController.class).getAnalyzerConfigurationsForProject(projectId)).withRel("list"));
        resource.add(linkTo(methodOn(AnalyzerConfigurationController.class).getSingleAnalyzerConfigurationForProject(projectId, entity.getId())).withRel("self"));
        return resource;
    }

    public AnalyzerConfiguration updateEntity(AnalyzerConfiguration entity, AnalyzerConfigurationResource resource, Project project){
        entity.setProject(project);
        entity.setEnabled(resource.isEnabled());
        entity.setAnalyzerName(resource.getAnalyzerName());
        return entity;
    }

    public List<AnalyzerConfigurationResource> toResourceList(List<AnalyzerConfiguration> entities) {
        List<AnalyzerConfigurationResource> resultList = new ArrayList<>();
        for (AnalyzerConfiguration entity : entities) {
            resultList.add(toResource(entity));
        }
        return resultList;
    }

    public AnalyzerConfiguration toEntity(AnalyzerConfigurationResource resource, Project project) {
        AnalyzerConfiguration entity = new AnalyzerConfiguration();
        entity.setAnalyzerName(resource.getAnalyzerName());
        entity.setEnabled(resource.isEnabled());
        entity.setProject(project);
        return entity;
    }
}
