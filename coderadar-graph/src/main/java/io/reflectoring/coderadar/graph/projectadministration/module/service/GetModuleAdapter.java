package io.reflectoring.coderadar.graph.projectadministration.module.service;

import io.reflectoring.coderadar.graph.projectadministration.module.repository.GetModuleRepository;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.port.driven.module.GetModulePort;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetModuleAdapter implements GetModulePort {
  private final GetModuleRepository getModuleRepository;

  @Autowired
  public GetModuleAdapter(GetModuleRepository getModuleRepository) {
    this.getModuleRepository = getModuleRepository;
  }

  @Override
  public Optional<Module> get(Long id) {
    return getModuleRepository.findById(id);
  }
}
