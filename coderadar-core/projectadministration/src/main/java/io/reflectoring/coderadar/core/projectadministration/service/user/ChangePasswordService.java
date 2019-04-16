package io.reflectoring.coderadar.core.projectadministration.service.user;

import io.reflectoring.coderadar.core.projectadministration.port.driven.user.ChangePasswordPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.ChangePasswordCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.ChangePasswordUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChangePasswordService implements ChangePasswordUseCase {

  private final ChangePasswordPort port;

  @Autowired
  public ChangePasswordService(ChangePasswordPort port) {
    this.port = port;
  }

  @Override
  public void changePassword(ChangePasswordCommand command) {
    port.changePassword(command.getId(), command.getNewPassword());
  }
}
