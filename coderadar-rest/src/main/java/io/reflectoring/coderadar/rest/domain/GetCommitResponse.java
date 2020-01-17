package io.reflectoring.coderadar.rest.domain;

import lombok.Data;

@Data
public class GetCommitResponse {
  private String name;
  private String author;
  private String comment;
  private Long timestamp;
  private Boolean analyzed;
}
