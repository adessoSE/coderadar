package io.reflectoring.coderadar.core.query.domain;

import lombok.Data;

@Data
public class Changes {

  private boolean renamed;

  private boolean modified;

  private boolean deleted;

  private boolean added;
}
