package io.reflectoring.coderadar.core.projectadministration.port.driven.module;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import java.util.Optional;

public interface GetModulePort {
  Optional<Module> get(Long id);
}
