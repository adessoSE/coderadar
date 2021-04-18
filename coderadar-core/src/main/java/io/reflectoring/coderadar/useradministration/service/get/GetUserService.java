package io.reflectoring.coderadar.useradministration.service.get;

import io.reflectoring.coderadar.domain.User;
import io.reflectoring.coderadar.useradministration.UserNotFoundException;
import io.reflectoring.coderadar.useradministration.port.driven.GetUserPort;
import io.reflectoring.coderadar.useradministration.port.driver.get.GetUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserService implements GetUserUseCase {
  private final GetUserPort getUserPort;

  @Override
  public User getUser(long id) {
    if (getUserPort.existsById(id)) {
      return getUserPort.getUser(id);
    } else {
      throw new UserNotFoundException(id);
    }
  }
}
