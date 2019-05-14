package io.reflectoring.coderadar.graph.projectadministration.user.service;

import io.reflectoring.coderadar.core.projectadministration.domain.User;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.RegisterUserPort;
import org.springframework.stereotype.Service;

@Service("RegisterUserServiceNeo4j")
public class RegisterUserService implements RegisterUserPort {
  @Override
  public Long register(User user) {
    return 1000L;
  }
}
