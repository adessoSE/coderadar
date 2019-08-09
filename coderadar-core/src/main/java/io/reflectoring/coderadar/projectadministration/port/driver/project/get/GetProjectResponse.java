package io.reflectoring.coderadar.projectadministration.port.driver.project.get;

import lombok.Data;

import java.util.Date;

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
