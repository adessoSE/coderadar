package io.reflectoring.coderadar.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommitResponse {
  private String hash;
  private long timestamp;
  private boolean analyzed;
  private String author;
  private String authorEmail;
  private String comment;
}
