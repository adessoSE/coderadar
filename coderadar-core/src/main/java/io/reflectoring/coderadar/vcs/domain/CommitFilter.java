package io.reflectoring.coderadar.vcs.domain;

import io.reflectoring.coderadar.analyzer.domain.Commit;

@FunctionalInterface
public interface CommitFilter {

  boolean shouldBeProcessed(Commit commit);
}
