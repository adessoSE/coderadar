package io.reflectoring.coderadar.core.projectadministration.port.driver.user;


import io.reflectoring.coderadar.core.projectadministration.domain.User;

public interface LoginUserUseCase {
    User login(LoginUserCommand command);
}
