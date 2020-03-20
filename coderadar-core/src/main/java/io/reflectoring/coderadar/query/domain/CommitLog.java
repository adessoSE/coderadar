package io.reflectoring.coderadar.query.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class CommitLog {
  private List<String> refs = new ArrayList<>();
  private String hash;
  private List<String> parents = new ArrayList<>();
  private CommitLogAuthor author;
  private String subject;
}
