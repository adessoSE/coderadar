package io.reflectoring.coderadar.projectadministration.service.module;

import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.port.driven.module.GetModulePort;
import io.reflectoring.coderadar.projectadministration.port.driver.module.get.GetModuleUseCase;
import org.springframework.stereotype.Service;

@Service
public class GetModuleService implements GetModuleUseCase {
  private final GetModulePort getModulePort;

  public GetModuleService(GetModulePort getModulePort) {
    this.getModulePort = getModulePort;
  }

  @Override
  public Module get(long id) {
    return getModulePort.get(id);
  }
}
