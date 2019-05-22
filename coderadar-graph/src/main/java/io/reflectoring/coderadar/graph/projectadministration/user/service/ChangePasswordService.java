package io.reflectoring.coderadar.graph.projectadministration.user.service;

import io.reflectoring.coderadar.core.projectadministration.domain.User;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.ChangePasswordPort;
import io.reflectoring.coderadar.graph.projectadministration.user.repository.ChangePasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ChangePasswordServiceNeo4j")
public class ChangePasswordService implements ChangePasswordPort {

  private final ChangePasswordRepository changePasswordRepository;

  @Autowired
  public ChangePasswordService(ChangePasswordRepository changePasswordRepository) {
    this.changePasswordRepository = changePasswordRepository;
  }

  @Override
  public void changePassword(User user) {
    changePasswordRepository.save(user);
  }
}
