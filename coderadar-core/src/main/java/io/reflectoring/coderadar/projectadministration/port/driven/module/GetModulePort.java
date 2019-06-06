package io.reflectoring.coderadar.projectadministration.port.driven.module;

import io.reflectoring.coderadar.projectadministration.domain.Module;
import java.util.Optional;

public interface GetModulePort {
  Optional<Module> get(Long id);
}
