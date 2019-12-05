package io.reflectoring.coderadar.graph.useradministration.adapter;

import io.reflectoring.coderadar.graph.useradministration.UserMapper;
import io.reflectoring.coderadar.graph.useradministration.domain.UserEntity;
import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import io.reflectoring.coderadar.useradministration.UserNotFoundException;
import io.reflectoring.coderadar.useradministration.domain.User;
import io.reflectoring.coderadar.useradministration.port.driven.LoadUserPort;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class LoadUserAdapter implements LoadUserPort {

  private final UserRepository userRepository;
  private final UserMapper userMapper = new UserMapper();

  public LoadUserAdapter(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User loadUser(Long id) {
    Optional<UserEntity> userEntity = userRepository.findById(id);
    if (userEntity.isPresent()) {
      return userMapper.mapNodeEntity(userEntity.get());
    } else {
      throw new UserNotFoundException(id);
    }
  }

  @Override
  public User loadUserByUsername(String username) {
    Optional<UserEntity> userEntity = userRepository.findByUsername(username);
    if (userEntity.isPresent()) {
      return userMapper.mapNodeEntity(userEntity.get());
    } else {
      throw new UserNotFoundException(username);
    }
  }

  @Override
  public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }
}
