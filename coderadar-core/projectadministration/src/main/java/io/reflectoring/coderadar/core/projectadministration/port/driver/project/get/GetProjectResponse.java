package io.reflectoring.coderadar.core.projectadministration.port.driver.project.get;

import lombok.Data;

import java.net.URL;
import java.util.Date;

@Data
public class GetProjectResponse {
  private Long id;
  private String name;
  private String vcsUsername;
  private String vcsPassword;
  private URL vcsUrl;
  private Boolean vcsOnline;
  private Date start;
  private Date end;
}
