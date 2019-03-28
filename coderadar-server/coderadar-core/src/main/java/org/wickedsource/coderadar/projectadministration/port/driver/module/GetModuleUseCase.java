package org.wickedsource.coderadar.projectadministration.port.driver.module;

import org.wickedsource.coderadar.projectadministration.domain.Module;

public interface GetModuleUseCase {
    Module get(GetModuleCommand command);
}
