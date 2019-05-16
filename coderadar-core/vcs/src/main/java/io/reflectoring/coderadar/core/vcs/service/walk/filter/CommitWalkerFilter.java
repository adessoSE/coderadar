package io.reflectoring.coderadar.core.vcs.service.walk.filter;

import org.eclipse.jgit.revwalk.RevCommit;

@FunctionalInterface
public interface CommitWalkerFilter {

  boolean shouldBeProcessed(RevCommit commit);
}
