package io.reflectoring.coderadar.graph.useradministration.adapter;

import io.reflectoring.coderadar.graph.useradministration.domain.UserEntity;
import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import io.reflectoring.coderadar.useradministration.UserNotFoundException;
import io.reflectoring.coderadar.useradministration.domain.User;
import io.reflectoring.coderadar.useradministration.port.driven.ChangePasswordPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChangePasswordAdapter implements ChangePasswordPort {

  private final UserRepository userRepository;

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
