package io.reflectoring.coderadar.core.projectadministration.port.driver.project.create;

import java.net.URL;
import java.util.Date;
import lombok.Value;

@Value
public class CreateProjectCommand {
  private String name;
  private String vcsUsername;
  private String vcsPassword;
  private URL vcsUrl;
  private Boolean vcsOnline;
  private Date start;
  private Date end;
}
