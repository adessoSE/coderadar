package io.reflectoring.coderadar.graph.projectadministration.user.service;

import io.reflectoring.coderadar.graph.projectadministration.domain.UserEntity;
import io.reflectoring.coderadar.graph.projectadministration.user.repository.ChangePasswordRepository;
import io.reflectoring.coderadar.projectadministration.UserNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.User;
import io.reflectoring.coderadar.projectadministration.port.driven.user.ChangePasswordPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChangePasswordAdapter implements ChangePasswordPort {

  private final ChangePasswordRepository changePasswordRepository;

  @Autowired
  public ChangePasswordAdapter(ChangePasswordRepository changePasswordRepository) {
    this.changePasswordRepository = changePasswordRepository;
  }

  @Override
  public void changePassword(User user) {
    Optional<UserEntity> userEntity = changePasswordRepository.findById(user.getId());
    if (userEntity.isPresent()) {
      userEntity.get().setPassword(user.getPassword());
      changePasswordRepository.save(userEntity.get());
    } else {
      throw new UserNotFoundException(user.getUsername());
    }
  }
}
