package org.wickedsource.coderadar.projectadministration.port.driver.user;

import org.wickedsource.coderadar.projectadministration.domain.User;

public interface LoadUserUseCase {
    LoadUserCommand loadUser(Long id);
}
