package io.reflectoring.coderadar.query.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommitResponse {
  private String hash;
  private String author;
  private String authorEmail;
  private String comment;
  private long timestamp;
  private boolean analyzed;
}
