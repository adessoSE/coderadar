package io.reflectoring.coderadar.graph.projectadministration.user.service;

import io.reflectoring.coderadar.graph.projectadministration.user.repository.ChangePasswordRepository;
import io.reflectoring.coderadar.projectadministration.domain.User;
import io.reflectoring.coderadar.projectadministration.port.driven.user.ChangePasswordPort;
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
