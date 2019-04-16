package io.reflectoring.coderadar.core.projectadministration.port.driver.project;

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
