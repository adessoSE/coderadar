package io.reflectoring.coderadar.core.projectadministration.port.driver.module;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;

public interface GetModuleUseCase {
    Module get(GetModuleCommand command);
}
