package io.reflectoring.coderadar.graph.useradministration.adapter.teams;

import io.reflectoring.coderadar.graph.useradministration.UserMapper;
import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import io.reflectoring.coderadar.useradministration.domain.User;
import io.reflectoring.coderadar.useradministration.port.driven.ListUsersForProjectPort;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ListUsersForProjectAdapter implements ListUsersForProjectPort {

  private final UserRepository userRepository;
  private final UserMapper userMapper = new UserMapper();

  public ListUsersForProjectAdapter(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<User> listUsers(long projectId) {
    return userMapper.mapNodeEntities(userRepository.listUsersForProject(projectId));
  }
}
