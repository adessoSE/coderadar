package org.wickedsource.coderadar.vcs;

import java.io.File;
import org.eclipse.jgit.api.Git;

public interface RepositoryCloner {

  /**
   * Clones a remote source code repository into a local git repository.
   *
   * @param remoteUrl the URL at which the remote repository is available.
   * @param localDir the local directory in which to put the local git repository. The directory
   *     will be created if it does not exist.
   * @return a JGit Git client pointing to the freshly cloned local git repository.
   */
  Git cloneRepository(String remoteUrl, File localDir);
}
