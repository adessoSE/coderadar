package io.reflectoring.coderadar.graph.projectadministration.user.service;

import io.reflectoring.coderadar.graph.projectadministration.domain.UserEntity;
import io.reflectoring.coderadar.graph.projectadministration.user.UserMapper;
import io.reflectoring.coderadar.graph.projectadministration.user.repository.UserRepository;
import io.reflectoring.coderadar.projectadministration.UserNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.User;
import io.reflectoring.coderadar.projectadministration.port.driven.user.LoadUserPort;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoadUserAdapter implements LoadUserPort {

  private final UserRepository userRepository;
  private final UserMapper userMapper = new UserMapper();

  @Autowired
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
    return userRepository.findByUsername(username).isPresent();
  }
}
