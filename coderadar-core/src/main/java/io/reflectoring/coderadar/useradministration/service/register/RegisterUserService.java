package io.reflectoring.coderadar.useradministration.service.register;

import io.reflectoring.coderadar.useradministration.UsernameAlreadyInUseException;
import io.reflectoring.coderadar.useradministration.domain.User;
import io.reflectoring.coderadar.useradministration.port.driven.GetUserPort;
import io.reflectoring.coderadar.useradministration.port.driven.ListUsersPort;
import io.reflectoring.coderadar.useradministration.port.driven.RegisterUserPort;
import io.reflectoring.coderadar.useradministration.port.driver.register.RegisterUserCommand;
import io.reflectoring.coderadar.useradministration.port.driver.register.RegisterUserUseCase;
import io.reflectoring.coderadar.useradministration.service.security.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUserService implements RegisterUserUseCase {

  private final RegisterUserPort port;
  private final GetUserPort getUserPort;
  private final ListUsersPort listUsersPort;

  private static final Logger logger = LoggerFactory.getLogger(RegisterUserService.class);

  @Override
  public Long register(RegisterUserCommand command) {
    if (getUserPort.existsByUsername(command.getUsername())) {
      throw new UsernameAlreadyInUseException(command.getUsername());
    }

    User user = new User();
    user.setUsername(command.getUsername());
    user.setPassword(PasswordUtil.hash(command.getPassword()));
    if (!listUsersPort.usersExist()) {
      user.setPlatformAdmin(true); // Only the first user to register gets the platform admin role
    }
    Long id = port.register(user);
    logger.info("Created user {}", user.getUsername());
    return id;
  }
}
