package org.wickedsource.coderadar.projectadministration.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.projectadministration.domain.User;
import org.wickedsource.coderadar.projectadministration.port.driven.user.ChangePasswordPort;
import org.wickedsource.coderadar.projectadministration.port.driver.user.ChangePasswordUseCase;

@Service
public class ChangePasswordService implements ChangePasswordUseCase {

  private final ChangePasswordPort port;

  @Autowired
  public ChangePasswordService(ChangePasswordPort port) {
    this.port = port;
  }

  @Override
  public User changePassword(String refreshToken, String newPassword) {
    return null;
  }
}
