package org.wickedsource.coderadar.graph.domain.commit;

import org.wickedsource.coderadar.graph.domain.Immutable;

public class CommitName extends Immutable<String> {

  private CommitName(String name) {
    super(name);
  }

  public static CommitName from(String name) {
    return new CommitName(name);
  }
}
