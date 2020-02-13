package io.reflectoring.coderadar.rest.domain;

import lombok.Data;

import java.util.Date;

@Data
public class GetProjectResponse {
  private long id;
  private String name;
  private String vcsUsername;
  private String vcsPassword;
  private String vcsUrl;
  private boolean vcsOnline;
  private Date startDate;
  private Date endDate;
}
