package io.reflectoring.coderadar.graph.projectadministration.user.service;

import io.reflectoring.coderadar.graph.projectadministration.domain.UserEntity;
import io.reflectoring.coderadar.graph.projectadministration.user.repository.UserRepository;
import io.reflectoring.coderadar.projectadministration.UserNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.User;
import io.reflectoring.coderadar.projectadministration.port.driven.user.ChangePasswordPort;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChangePasswordAdapter implements ChangePasswordPort {

  private final UserRepository userRepository;

  @Autowired
  public ChangePasswordAdapter(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void changePassword(User user) {
    Optional<UserEntity> userEntity = userRepository.findById(user.getId());
    if (userEntity.isPresent()) {
      userEntity.get().setPassword(user.getPassword());
      userRepository.save(userEntity.get());
    } else {
      throw new UserNotFoundException(user.getUsername());
    }
  }
}
