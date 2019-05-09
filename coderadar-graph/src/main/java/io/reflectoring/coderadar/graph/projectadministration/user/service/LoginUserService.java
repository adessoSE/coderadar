package io.reflectoring.coderadar.graph.projectadministration.user.service;

import io.reflectoring.coderadar.core.projectadministration.domain.User;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.LoginUserPort;
import org.springframework.stereotype.Service;

@Service("LoginUserServiceNeo4j")
public class LoginUserService implements LoginUserPort {
    @Override
    public User login(User user) {
        return user;
    }
}
