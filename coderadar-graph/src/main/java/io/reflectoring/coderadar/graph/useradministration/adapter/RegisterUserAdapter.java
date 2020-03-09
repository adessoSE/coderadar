package io.reflectoring.coderadar.graph.useradministration.adapter;

import io.reflectoring.coderadar.graph.useradministration.UserMapper;
import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import io.reflectoring.coderadar.useradministration.domain.User;
import io.reflectoring.coderadar.useradministration.port.driven.RegisterUserPort;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserAdapter implements RegisterUserPort {

  private final UserRepository userRepository;
  private final UserMapper userMapper = new UserMapper();

  public RegisterUserAdapter(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public long register(User user) {
    return userRepository.save(userMapper.mapDomainObject(user)).getId();
  }
}
