package io.reflectoring.coderadar.vcs.domain;

@FunctionalInterface
public interface CommitProcessor {
  void processCommit(VcsCommit commit);
}
