package io.reflectoring.coderadar.core.projectadministration.port.driver.project.update;

import lombok.Value;

import java.net.URL;
import java.util.Date;

@Value
public class UpdateProjectCommand {
  private String name;
  private String vcsUsername;
  private String vcsPassword;
  private URL vcsUrl;
  private Boolean vcsOnline;
  private Date start;
  private Date end;
}
