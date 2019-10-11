package io.reflectoring.coderadar.query.port.driver;

import lombok.Data;

@Data
public class GetCommitResponse {
  private String name;
  private String author;
  private String comment;
  private Long timestamp;
  private Boolean analyzed;
}
