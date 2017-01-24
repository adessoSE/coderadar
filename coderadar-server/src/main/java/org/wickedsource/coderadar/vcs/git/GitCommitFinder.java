package org.wickedsource.coderadar.vcs.git;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.stereotype.Service;

@Service
public class GitCommitFinder {

  /**
   * Returns the commit with the given name from the given repository. Returns null, if the commit
   * does not exist.
   *
   * @param gitClient the git client to access the repository.
   * @param commitName the name of the commit that should be found.
   * @return object representing the commit in question or null if it was not found.
   */
  public RevCommit findCommit(Git gitClient, String commitName) {
    try {
      ObjectId commitId = gitClient.getRepository().resolve(commitName);
      Iterable<RevCommit> commits = gitClient.log().add(commitId).call();
      return commits.iterator().next();
    } catch (MissingObjectException e) {
      return null;
    } catch (Exception e) {
      throw new IllegalStateException(
          String.format(
              "error accessing git repository at %s",
              gitClient.getRepository().getDirectory().getAbsolutePath()),
          e);
    }
  }
}
