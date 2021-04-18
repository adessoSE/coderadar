package io.reflectoring.coderadar.domain;

import java.util.Collections;
import java.util.List;
import lombok.Data;

@Data
public class CommitLog {
  private List<String> refs = Collections.emptyList();
  private String hash;
  private String[] parents;
  private CommitLogAuthor author;
  private String subject;
  private boolean analyzed;
}
