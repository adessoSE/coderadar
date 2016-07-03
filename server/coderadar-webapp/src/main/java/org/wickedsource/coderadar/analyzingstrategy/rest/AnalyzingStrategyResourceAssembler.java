package org.wickedsource.coderadar.analyzingstrategy.rest;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.wickedsource.coderadar.analyzingstrategy.domain.AnalyzingStrategy;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.rest.ProjectController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class AnalyzingStrategyResourceAssembler extends ResourceAssemblerSupport<AnalyzingStrategy, AnalyzingStrategyResource> {

    private Long projectId;

    AnalyzingStrategyResourceAssembler(Long projectId) {
        super(AnalyzingStrategyController.class, AnalyzingStrategyResource.class);
        this.projectId = projectId;
    }

    @Override
    public AnalyzingStrategyResource toResource(AnalyzingStrategy entity) {
        AnalyzingStrategyResource resource = new AnalyzingStrategyResource(entity.getFromDate(), entity.isActive());
        resource.add(linkTo(methodOn(AnalyzingStrategyController.class).getAnalyzingStrategy(projectId)).withRel("self"));
        resource.add(linkTo(methodOn(ProjectController.class).getProject(projectId)).withRel("project"));
        return resource;
    }

    public AnalyzingStrategy updateEntity(AnalyzingStrategy entity, AnalyzingStrategyResource resource, Project project){
        entity.setProject(project);
        entity.setActive(resource.isActive());
        entity.setFromDate(resource.getFromDate());
        return entity;
    }
}
