package org.wickedsource.coderadar.vcs.git;

import java.nio.file.Path;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.project.domain.VcsCoordinates;

@Service
public class GitCommitFetcher {

  /**
   * Fetches the specified commit from a remote GIT repository to a local directory.
   *
   * @param commitName name/id of the commit to fetch.
   * @param vcsCoordinates the coordinates of the remote GIT repository.
   * @param localDir the local directory in which to store the local copy of the commit.
   */
  public void fetchCommit(String commitName, VcsCoordinates vcsCoordinates, Path localDir) {
    try {
      // cloning repository without content
      Git gitClient =
          Git.cloneRepository()
              .setNoCheckout(true)
              .setDirectory(localDir.toFile())
              .setURI(vcsCoordinates.getUrl().toString())
              .call();

      // checking out the specified commit
      gitClient.checkout().setName(commitName).call();

      gitClient.getRepository().close();
    } catch (GitAPIException e) {
      throw new IllegalStateException(
          String.format(
              "error while fetching commit into directory %s from vcs coordinates %s",
              localDir, vcsCoordinates),
          e);
    }
  }
}
