package io.reflectoring.coderadar.core.projectadministration.port.driver.module;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import java.util.Optional;

public interface GetModuleUseCase {
  Optional<Module> get(GetModuleCommand command);
}
