package io.reflectoring.coderadar.graph.useradministration.adapter;

import io.reflectoring.coderadar.graph.useradministration.UserMapper;
import io.reflectoring.coderadar.graph.useradministration.domain.UserEntity;
import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import io.reflectoring.coderadar.useradministration.UserNotFoundException;
import io.reflectoring.coderadar.useradministration.domain.User;
import io.reflectoring.coderadar.useradministration.port.driven.LoadUserPort;
import org.springframework.stereotype.Service;

@Service
public class LoadUserAdapter implements LoadUserPort {

  private final UserRepository userRepository;
  private final UserMapper userMapper = new UserMapper();

  public LoadUserAdapter(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User loadUser(long id) {
    return userMapper.mapNodeEntity(
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id)));
  }

  @Override
  public User loadUserByUsername(String username) {
    UserEntity userEntity = userRepository.findByUsername(username);
    if (userEntity == null) {
      throw new UserNotFoundException(username);
    } else {
      return userMapper.mapNodeEntity(userEntity);
    }
  }

  @Override
  public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  @Override
  public boolean existsById(long id) {
    return userRepository.existsById(id);
  }
}
