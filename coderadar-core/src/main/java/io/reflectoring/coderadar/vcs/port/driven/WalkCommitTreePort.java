package io.reflectoring.coderadar.vcs.port.driven;

import io.reflectoring.coderadar.vcs.UnableToWalkCommitTreeException;

public interface WalkCommitTreePort {

  /**
   * Execute a TreeWalk for a given Commit in a given Repository.
   *
   * @param projectRoot
   * @param name
   */
  void walkCommitTree(String projectRoot, String name, WalkTreeCommandInterface commandInterface)
      throws UnableToWalkCommitTreeException;
}
