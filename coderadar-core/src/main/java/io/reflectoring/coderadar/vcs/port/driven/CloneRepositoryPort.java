package io.reflectoring.coderadar.vcs.port.driven;

import io.reflectoring.coderadar.vcs.UnableToCloneRepositoryException;

import java.io.File;

public interface CloneRepositoryPort {

  /**
   * Clones a remote git repository to the local working directory
   *
   * @param remoteUrl The URL of the remove
   * @param localDir The local directory to clone to
   * @throws UnableToCloneRepositoryException Thrown if there is an error while cloning the
   *     repository.
   */
  void cloneRepository(String remoteUrl, File localDir) throws UnableToCloneRepositoryException;
}
