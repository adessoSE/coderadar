package io.reflectoring.coderadar.query.domain;

import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class CommitLog {
  private List<String> refs = Collections.emptyList();
  private String hash;
  private String[] parents;
  private CommitLogAuthor author;
  private String subject;
  private boolean analyzed;
}
