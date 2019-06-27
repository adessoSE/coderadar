package io.reflectoring.coderadar.graph.projectadministration.user.service;

import io.reflectoring.coderadar.graph.projectadministration.domain.UserEntity;
import io.reflectoring.coderadar.graph.projectadministration.user.UserMapper;
import io.reflectoring.coderadar.graph.projectadministration.user.repository.LoadUserRepository;
import io.reflectoring.coderadar.projectadministration.UserNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.User;
import io.reflectoring.coderadar.projectadministration.port.driven.user.LoadUserPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoadUserAdapter implements LoadUserPort {

  private final LoadUserRepository loadUserRepository;
  private final UserMapper userMapper = new UserMapper();

  @Autowired
  public LoadUserAdapter(LoadUserRepository loadUserRepository) {
    this.loadUserRepository = loadUserRepository;
  }

  @Override
  public User loadUser(Long id) {
    Optional<UserEntity> userEntity = loadUserRepository.findById(id);
    if (userEntity.isPresent()) {
      return userMapper.mapNodeEntity(userEntity.get());
    } else {
      throw new UserNotFoundException(id);
    }
  }

  @Override
  public User loadUserByUsername(String username) {
    Optional<UserEntity> userEntity = loadUserRepository.findByUsername(username);
    if (userEntity.isPresent()) {
      return userMapper.mapNodeEntity(userEntity.get());
    } else {
      throw new UserNotFoundException(username);
    }
  }

  @Override
  public boolean existsByUsername(String username) {
    return loadUserRepository.findByUsername(username).isPresent();
  }
}
