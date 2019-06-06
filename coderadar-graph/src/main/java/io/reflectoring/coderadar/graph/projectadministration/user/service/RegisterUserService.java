package io.reflectoring.coderadar.graph.projectadministration.user.service;

import io.reflectoring.coderadar.graph.projectadministration.user.repository.RegisterUserRepository;
import io.reflectoring.coderadar.projectadministration.domain.User;
import io.reflectoring.coderadar.projectadministration.port.driven.user.RegisterUserPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("RegisterUserServiceNeo4j")
public class RegisterUserService implements RegisterUserPort {

  private RegisterUserRepository registerUserRepository;

  @Autowired
  public RegisterUserService(RegisterUserRepository registerUserRepository) {
    this.registerUserRepository = registerUserRepository;
  }

  @Override
  public Long register(User user) {
    return registerUserRepository.save(user).getId();
  }
}
