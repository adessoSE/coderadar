package io.reflectoring.coderadar.vcs.adapter;

import io.reflectoring.coderadar.vcs.UnableToCloneRepositoryException;
import io.reflectoring.coderadar.vcs.port.driven.CloneRepositoryPort;
import java.io.File;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.stereotype.Service;

@Service
public class CloneRepositoryAdapter implements CloneRepositoryPort {

  @Override
  public void cloneRepository(String remoteUrl, File localDir)
      throws UnableToCloneRepositoryException {
    try {
      // TODO: support cloning with credentials for private repositories
      // TODO: support progress monitoring
      Git git = Git.cloneRepository().setURI(remoteUrl).setDirectory(localDir).call();
      git.close();
    } catch (GitAPIException e) {
      throw new UnableToCloneRepositoryException(e.getMessage());
    }
  }
}
