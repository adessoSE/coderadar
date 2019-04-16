package io.reflectoring.coderadar.core.projectadministration.port.driver.project;

import lombok.Value;

import java.net.URL;
import java.util.Date;

@Value
public class UpdateProjectCommand {
    Long id;
    String name;
    String workdir;
    String vcsUsername;
    String vcsPassword;
    URL vcsUrl;
    Boolean vcsOnline;
    Date start;
    Date end;
}
