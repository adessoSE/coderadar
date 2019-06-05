package io.reflectoring.coderadar.core.vcs.port.driver.walk.walkCommit;

import io.reflectoring.coderadar.core.vcs.service.walk.filter.CommitWalkerFilter;

public interface WalkCommitsUseCase {
  void walk(WalkCommitsCommand command);

  void addFilter(CommitWalkerFilter filter);
}
