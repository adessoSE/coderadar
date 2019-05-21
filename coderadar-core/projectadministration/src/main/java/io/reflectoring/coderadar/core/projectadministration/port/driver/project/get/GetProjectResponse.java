package io.reflectoring.coderadar.core.projectadministration.port.driver.project.get;

import java.net.URL;
import java.util.Date;
import lombok.Data;

@Data
public class GetProjectResponse {
  private Long id;
  private String name;
  private String vcsUsername;
  private String vcsPassword;
  private String vcsUrl;
  private Boolean vcsOnline;
  private Date start;
  private Date end;
}
