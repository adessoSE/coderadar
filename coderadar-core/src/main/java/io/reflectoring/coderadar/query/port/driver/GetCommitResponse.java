package io.reflectoring.coderadar.query.port.driver;

import lombok.Data;

@Data
public class GetCommitResponse {
  private String name;
  private String author;
  private String comment;
  private String timestamp;
  private Boolean analyzed;
}
