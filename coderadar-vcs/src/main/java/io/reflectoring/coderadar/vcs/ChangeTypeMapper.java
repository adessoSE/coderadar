package io.reflectoring.coderadar.vcs;

import io.reflectoring.coderadar.plugin.api.ChangeType;
import org.eclipse.jgit.diff.DiffEntry;

public class ChangeTypeMapper {

  private ChangeTypeMapper() {}

  public static ChangeType jgitToCoderadar(DiffEntry.ChangeType changeType) {
    return ChangeType.valueOf(changeType.name());
  }

  public static DiffEntry.ChangeType coderadarToJgit(ChangeType changeType) {
    return DiffEntry.ChangeType.valueOf(changeType.name());
  }
}
