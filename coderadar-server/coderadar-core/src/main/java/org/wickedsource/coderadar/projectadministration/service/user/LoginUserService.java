package org.wickedsource.coderadar.projectadministration.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.projectadministration.port.driven.user.LoginUserPort;
import org.wickedsource.coderadar.projectadministration.port.driver.user.LoginUserUseCase;

@Service
public class LoginUserService implements LoginUserUseCase {

  private final LoginUserPort port;

  @Autowired
  public LoginUserService(LoginUserPort port) {
    this.port = port;
  }
}
