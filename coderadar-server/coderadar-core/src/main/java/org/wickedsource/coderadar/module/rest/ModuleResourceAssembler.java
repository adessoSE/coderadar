package org.wickedsource.coderadar.module.rest;

import org.wickedsource.coderadar.core.rest.AbstractResourceAssembler;
import org.wickedsource.coderadar.module.domain.Module;
import org.wickedsource.coderadar.project.domain.Project;

public class ModuleResourceAssembler extends AbstractResourceAssembler<Module, ModuleResource> {

  private Project project;

  public ModuleResourceAssembler(Project project) {
    this.project = project;
  }

  @Override
  public ModuleResource toResource(Module entity) {
    ModuleResource resource = new ModuleResource();
    resource.setModulePath(entity.getPath());
    resource.setId(entity.getId());
    return resource;
  }

  public Module updateEntity(Module entity, ModuleResource resource) {
    entity.setProject(project);
    entity.setPath(resource.getModulePath());
    return entity;
  }
}
