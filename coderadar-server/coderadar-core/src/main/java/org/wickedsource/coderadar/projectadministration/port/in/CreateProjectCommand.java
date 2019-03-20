package org.wickedsource.coderadar.projectadministration.port.in;

import java.net.URL;
import java.util.Date;
import lombok.Value;

@Value
public class CreateProjectCommand {
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
