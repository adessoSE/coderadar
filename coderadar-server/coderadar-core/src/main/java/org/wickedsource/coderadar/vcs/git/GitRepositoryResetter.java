package org.wickedsource.coderadar.vcs.git;

import java.nio.file.Path;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.stereotype.Service;

@Service
public class GitRepositoryResetter {

  /** Removes any staged changes that are not yet committed from a git repository. */
  public Git reset(Path repositoryRoot) {
    try {
      FileRepositoryBuilder builder = new FileRepositoryBuilder();
      Repository repository = builder.setWorkTree(repositoryRoot.toFile()).build();
      Git git = new Git(repository);
      git.reset().setMode(ResetCommand.ResetType.HARD).call();
      return git;
    } catch (Exception e) {
      throw new IllegalStateException(
          String.format("error resetting local GIT repository at %s", repositoryRoot), e);
    }
  }
}
