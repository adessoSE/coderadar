package io.reflectoring.coderadar.graph.useradministration.adapter.teams;

import io.reflectoring.coderadar.graph.useradministration.UserMapper;
import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import io.reflectoring.coderadar.useradministration.domain.User;
import io.reflectoring.coderadar.useradministration.port.driven.ListUsersForProjectPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListUsersForProjectAdapter implements ListUsersForProjectPort {

  private final UserRepository userRepository;
  private final UserMapper userMapper = new UserMapper();

  @Override
  public List<User> listUsers(long projectId) {
    return userMapper.mapNodeEntities(userRepository.listUsersForProject(projectId));
  }
}
