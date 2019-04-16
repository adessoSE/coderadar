package io.reflectoring.coderadar.core.projectadministration.port.driver.user;

import lombok.Value;

@Value
public class ChangePasswordCommand {
    Long id;
    String newPassword;
}
