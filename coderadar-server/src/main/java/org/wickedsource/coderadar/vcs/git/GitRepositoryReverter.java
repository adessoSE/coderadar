package org.wickedsource.coderadar.vcs.git;

import java.nio.file.Path;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.stereotype.Service;

@Service
public class GitRepositoryReverter {

  public Git revertRepository(Path repositoryRoot) {
    try {
      FileRepositoryBuilder builder = new FileRepositoryBuilder();
      Repository repository = builder.setWorkTree(repositoryRoot.toFile()).build();
      Git git = new Git(repository);
      git.revert().call();
      return git;
    } catch (Exception e) {
      throw new IllegalStateException(
          String.format("error reverting local GIT repository at %s", repositoryRoot), e);
    }
  }
}
