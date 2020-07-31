package io.reflectoring.coderadar.rest.domain;

import lombok.Data;

@Data
public class GetCommitResponse {
  private String hash;
  private String author;
  private String authorEmail;
  private String comment;
  private long timestamp;
  private boolean analyzed;
}
