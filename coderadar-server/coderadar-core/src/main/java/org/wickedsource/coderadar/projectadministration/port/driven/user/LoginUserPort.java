package org.wickedsource.coderadar.projectadministration.port.driven.user;

import org.wickedsource.coderadar.projectadministration.domain.User;

public interface LoginUserPort {
  User login(User user);
}