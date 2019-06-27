package io.reflectoring.coderadar.vcs.domain;

@FunctionalInterface
public interface CommitFilter {

  boolean shouldBeProcessed(VcsCommit commit);
}
