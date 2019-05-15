package io.reflectoring.coderadar.core.projectadministration.service.user;

import io.reflectoring.coderadar.core.projectadministration.domain.User;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.LoginUserPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.login.LoginUserCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.login.LoginUserResponse;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.login.LoginUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("LoginUserService")
public class LoginUserService implements LoginUserUseCase {

  private final LoginUserPort port;

  @Autowired
  public LoginUserService(@Qualifier("LoginUserServiceNeo4j") LoginUserPort port) {
    this.port = port;
  }

  @Override
  public LoginUserResponse login(LoginUserCommand command) {
    User user = new User();
    user.setUsername(command.getUsername());
    user.setPassword(command.getPassword());
    user = port.login(user);
    return new LoginUserResponse("abalfgubhfuo[oi3y0823pdyu", "ift021789f21897f2187fg");
  }
}
