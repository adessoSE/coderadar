package io.reflectoring.coderadar.query.domain;

import java.util.Collections;
import java.util.List;
import lombok.Data;

@Data
public class CommitLog {
  private List<String> refs = Collections.emptyList();
  private String hash;
  private String[] parents = new String[0];
  private CommitLogAuthor author;
  private String subject;
  private boolean analyzed;
}
