package io.reflectoring.coderadar.projectadministration.service.module;

import io.reflectoring.coderadar.projectadministration.ModuleNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Module;
import io.reflectoring.coderadar.projectadministration.port.driven.module.GetModulePort;
import io.reflectoring.coderadar.projectadministration.port.driver.module.get.GetModuleResponse;
import io.reflectoring.coderadar.projectadministration.port.driver.module.get.GetModuleUseCase;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("GetModuleService")
public class GetModuleService implements GetModuleUseCase {
  private final GetModulePort getModulePort;

  @Autowired
  public GetModuleService(@Qualifier("GetModuleServiceNeo4j") GetModulePort getModulePort) {
    this.getModulePort = getModulePort;
  }

  @Override
  public GetModuleResponse get(Long id) throws ModuleNotFoundException {
    Optional<Module> module = getModulePort.get(id);

    if (module.isPresent()) {
      return new GetModuleResponse(id, module.get().getPath());
    } else {
      throw new ModuleNotFoundException(id);
    }
  }
}
