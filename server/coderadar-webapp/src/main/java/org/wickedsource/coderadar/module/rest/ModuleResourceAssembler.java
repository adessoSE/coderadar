package org.wickedsource.coderadar.module.rest;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.wickedsource.coderadar.module.domain.Module;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.rest.ProjectController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class ModuleResourceAssembler extends ResourceAssemblerSupport<Module, ModuleResource> {

    private Project project;

    public ModuleResourceAssembler(Project project) {
        super(ModuleController.class, ModuleResource.class);
        this.project = project;
    }

    @Override
    public ModuleResource toResource(Module entity) {
        ModuleResource resource = new ModuleResource();
        resource.setModulePath(entity.getPath());
        resource.add(linkTo(methodOn(ModuleController.class).getModule(entity.getId(), project.getId())).withRel("self"));
        resource.add(linkTo(methodOn(ProjectController.class).getProject(project.getId())).withRel("project"));
        return resource;
    }

    public Module updateEntity(Module entity, ModuleResource resource) {
        entity.setProject(project);
        entity.setPath(resource.getModulePath());
        return entity;
    }

}
