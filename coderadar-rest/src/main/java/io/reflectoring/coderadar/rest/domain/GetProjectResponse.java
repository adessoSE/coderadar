package io.reflectoring.coderadar.rest.domain;

import java.util.Date;
import lombok.Data;

@Data
public class GetProjectResponse {
  private long id;
  private String name;
  private String vcsUsername;
  private String vcsPassword;
  private String vcsUrl;
  private Date startDate;
}
