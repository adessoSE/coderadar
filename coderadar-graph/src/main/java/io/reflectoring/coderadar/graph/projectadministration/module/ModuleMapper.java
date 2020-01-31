package io.reflectoring.coderadar.graph.projectadministration.module;

import io.reflectoring.coderadar.graph.Mapper;
import io.reflectoring.coderadar.graph.projectadministration.domain.ModuleEntity;
import io.reflectoring.coderadar.projectadministration.domain.Module;

public class ModuleMapper implements Mapper<Module, ModuleEntity> {

  @Override
  public Module mapNodeEntity(ModuleEntity nodeEntity) {
    Module module = new Module();
    module.setId(nodeEntity.getId());
    module.setPath(nodeEntity.getPath());
    return module;
  }

  @Override
  public ModuleEntity mapDomainObject(Module domainObject) {
    ModuleEntity moduleEntity = new ModuleEntity();
    moduleEntity.setPath(domainObject.getPath());
    return moduleEntity;
  }
}
