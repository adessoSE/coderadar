package io.reflectoring.coderadar.graph.projectadministration.user.service;

import io.reflectoring.coderadar.graph.projectadministration.user.UserMapper;
import io.reflectoring.coderadar.graph.projectadministration.user.repository.UserRepository;
import io.reflectoring.coderadar.projectadministration.domain.User;
import io.reflectoring.coderadar.projectadministration.port.driven.user.RegisterUserPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserAdapter implements RegisterUserPort {

  private final UserRepository userRepository;
  private final UserMapper userMapper = new UserMapper();

  @Autowired
  public RegisterUserAdapter(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public Long register(User user) {
    return userRepository.save(userMapper.mapDomainObject(user)).getId();
  }
}
