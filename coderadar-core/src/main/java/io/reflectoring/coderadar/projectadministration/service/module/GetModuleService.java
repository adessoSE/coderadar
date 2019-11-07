package io.reflectoring.coderadar.projectadministration.service.module;

import io.reflectoring.coderadar.projectadministration.port.driven.module.GetModulePort;
import io.reflectoring.coderadar.projectadministration.port.driver.module.get.GetModuleResponse;
import io.reflectoring.coderadar.projectadministration.port.driver.module.get.GetModuleUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetModuleService implements GetModuleUseCase {
  private final GetModulePort getModulePort;

  @Autowired
  public GetModuleService(GetModulePort getModulePort) {
    this.getModulePort = getModulePort;
  }

  @Override
  public GetModuleResponse get(Long id) {
    return new GetModuleResponse(id, getModulePort.get(id).getPath());
  }
}
