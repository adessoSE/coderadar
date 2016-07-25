package org.wickedsource.coderadar.analyzer.rest.analyzerconfiguration;

import org.wickedsource.coderadar.analyzer.domain.AnalyzerConfiguration;
import org.wickedsource.coderadar.core.rest.AbstractResourceAssembler;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.rest.ProjectController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class AnalyzerConfigurationResourceAssembler extends AbstractResourceAssembler<AnalyzerConfiguration, AnalyzerConfigurationResource> {

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

    public AnalyzerConfiguration toEntity(AnalyzerConfigurationResource resource, Project project) {
        return updateEntity(resource, project, new AnalyzerConfiguration());
    }

    public AnalyzerConfiguration updateEntity(AnalyzerConfigurationResource resource, Project project, AnalyzerConfiguration entity) {
        entity.setAnalyzerName(resource.getAnalyzerName());
        entity.setEnabled(resource.isEnabled());
        entity.setProject(project);
        return entity;
    }
}
