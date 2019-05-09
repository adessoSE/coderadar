package io.reflectoring.coderadar.graph.projectadministration.user.service;

import io.reflectoring.coderadar.core.projectadministration.domain.User;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.LoadUserPort;
import org.springframework.stereotype.Service;

@Service("LoadUserServiceNeo4j")
public class LoadUserService implements LoadUserPort {
  @Override
  public User loadUser(Long id) {
    return new User();
  }
}
