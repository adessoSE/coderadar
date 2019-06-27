package io.reflectoring.coderadar.projectadministration.port.driver.project.get;

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
