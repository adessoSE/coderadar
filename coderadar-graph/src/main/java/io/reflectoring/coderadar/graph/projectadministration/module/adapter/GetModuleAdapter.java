package io.reflectoring.coderadar.graph.projectadministration.module.adapter;

import io.reflectoring.coderadar.domain.Module;
import io.reflectoring.coderadar.graph.projectadministration.module.ModuleMapper;
import io.reflectoring.coderadar.graph.projectadministration.module.repository.ModuleRepository;
import io.reflectoring.coderadar.projectadministration.ModuleNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.module.GetModulePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetModuleAdapter implements GetModulePort {
  private final ModuleRepository moduleRepository;
  private final ModuleMapper moduleMapper = new ModuleMapper();

  @Override
  public Module get(long id) {
    return moduleMapper.mapGraphObject(
        moduleRepository.findById(id, 0).orElseThrow(() -> new ModuleNotFoundException(id)));
  }
}
