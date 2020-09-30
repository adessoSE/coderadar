package io.reflectoring.coderadar.useradministration.service.delete;

import io.reflectoring.coderadar.useradministration.UserNotFoundException;
import io.reflectoring.coderadar.useradministration.port.driven.DeleteUserPort;
import io.reflectoring.coderadar.useradministration.port.driven.GetUserPort;
import io.reflectoring.coderadar.useradministration.port.driver.delete.DeleteUserUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserService implements DeleteUserUseCase {

  private final GetUserPort getUserPort;
  private final DeleteUserPort deleteUserPort;
  private static final Logger logger = LoggerFactory.getLogger(DeleteUserService.class);

  @Override
  public void deleteUser(long userId) {
    if (getUserPort.existsById(userId)) {
      deleteUserPort.deleteUser(userId);
      logger.info("Deleted user with id: {}", userId);
    } else {
      throw new UserNotFoundException(userId);
    }
  }
}
