package io.reflectoring.coderadar.vcs.domain;

import io.reflectoring.coderadar.analyzer.domain.Commit;

import java.io.IOException;

@FunctionalInterface
public interface CommitProcessor {
  void processCommit(Commit commit) throws IOException;
}
