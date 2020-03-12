package io.reflectoring.coderadar.vcs.adapter;

import io.reflectoring.coderadar.vcs.UnableToCloneRepositoryException;
import io.reflectoring.coderadar.vcs.port.driven.CloneRepositoryPort;
import io.reflectoring.coderadar.vcs.port.driver.clone.CloneRepositoryCommand;
import java.io.File;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Service;

@Service
public class CloneRepositoryAdapter implements CloneRepositoryPort {

  @Override
  public void cloneRepository(CloneRepositoryCommand cloneRepositoryCommand)
      throws UnableToCloneRepositoryException {
    try {
      // TODO: support progress monitoring
      CloneCommand cloneCommand =
          Git.cloneRepository()
              .setURI(cloneRepositoryCommand.getRemoteUrl())
              .setDirectory(new File(cloneRepositoryCommand.getLocalDir()));
      if (cloneRepositoryCommand.getUsername() != null
          && cloneRepositoryCommand.getPassword() != null) {
        cloneCommand.setCredentialsProvider(
            new UsernamePasswordCredentialsProvider(
                cloneRepositoryCommand.getUsername(), cloneRepositoryCommand.getPassword()));
      }
      cloneCommand.call().close();
    } catch (GitAPIException e) {
      throw new UnableToCloneRepositoryException(e.getMessage());
    }
  }
}
