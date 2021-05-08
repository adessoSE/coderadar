package io.reflectoring.coderadar.graph.useradministration.adapter;

import io.reflectoring.coderadar.domain.User;
import io.reflectoring.coderadar.graph.useradministration.UserMapper;
import io.reflectoring.coderadar.graph.useradministration.domain.UserEntity;
import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import io.reflectoring.coderadar.useradministration.UserNotFoundException;
import io.reflectoring.coderadar.useradministration.port.driven.GetUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserAdapter implements GetUserPort {

  private final UserRepository userRepository;
  private final UserMapper userMapper = new UserMapper();

  @Override
  public User getUser(long id) {
    return userMapper.mapGraphObject(
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id)));
  }

  @Override
  public User getUserByUsername(String username) {
    UserEntity userEntity = userRepository.findByUsername(username);
    if (userEntity == null) {
      throw new UserNotFoundException(username);
    } else {
      return userMapper.mapGraphObject(userEntity);
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
