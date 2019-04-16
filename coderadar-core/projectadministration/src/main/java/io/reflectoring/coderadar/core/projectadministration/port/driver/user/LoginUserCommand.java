package io.reflectoring.coderadar.core.projectadministration.port.driver.user;

import lombok.Value;

@Value
public class LoginUserCommand {
    private String username;
    private String password;
}
