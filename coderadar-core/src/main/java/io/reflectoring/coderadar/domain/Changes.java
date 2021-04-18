package io.reflectoring.coderadar.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class Changes {

  public static final Changes CHANGES_ADDED = new Changes(false, false, false, true);
  public static final Changes CHANGES_DELETED = new Changes(false, false, true, false);
  public static final Changes CHANGES_MODIFIED = new Changes(false, true, false, false);
  public static final Changes CHANGES_RENAMED = new Changes(true, false, false, false);
  public static final Changes CHANGES_UNCHANGED = new Changes(false, false, false, false);

  private boolean renamed;
  private boolean modified;
  private boolean deleted;
  private boolean added;
}
