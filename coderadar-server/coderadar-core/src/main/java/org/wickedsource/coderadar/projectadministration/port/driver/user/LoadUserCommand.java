package org.wickedsource.coderadar.projectadministration.port.driver.user;

import lombok.Value;

@Value
public class LoadUserCommand {
    private Long id;
    private String username;
}
