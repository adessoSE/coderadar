package io.reflectoring.coderadar.vcs.domain;

import java.io.IOException;

@FunctionalInterface
public interface CommitProcessor {
  void processCommit(VcsCommit commit) throws IOException;
}
