package org.wickedsource.coderadar.projectadministration.port.driven.module;

import org.wickedsource.coderadar.projectadministration.domain.Module;

public interface GetModulePort {
    Module get(Long id);
}
