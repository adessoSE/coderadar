package io.reflectoring.coderadar.query.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
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

  @Override
  public boolean equals(Object other) {
    return other instanceof Changes
        && renamed == ((Changes) other).renamed
        && modified == ((Changes) other).modified
        && deleted == ((Changes) other).deleted
        && added == ((Changes) other).added;
  }
}
