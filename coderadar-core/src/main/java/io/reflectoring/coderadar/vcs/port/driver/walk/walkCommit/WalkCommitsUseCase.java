package io.reflectoring.coderadar.vcs.port.driver.walk.walkCommit;

import io.reflectoring.coderadar.vcs.service.walk.filter.CommitWalkerFilter;

public interface WalkCommitsUseCase {
  void walk(WalkCommitsCommand command);

  void addFilter(CommitWalkerFilter filter);
}
