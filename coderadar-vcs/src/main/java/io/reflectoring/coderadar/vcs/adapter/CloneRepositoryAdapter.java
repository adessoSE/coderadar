package io.reflectoring.coderadar.vcs.adapter;

import io.reflectoring.coderadar.vcs.UnableToCloneRepositoryException;
import io.reflectoring.coderadar.vcs.port.driven.CloneRepositoryPort;
import java.io.File;
import java.io.IOException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.StoredConfig;
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
      StoredConfig config = git.getRepository().getConfig();
      config.setBoolean(
          ConfigConstants.CONFIG_CORE_SECTION, null, ConfigConstants.CONFIG_KEY_AUTOCRLF, true);
      config.save();
      git.getRepository().close();
    } catch (GitAPIException | IOException e) {
      throw new UnableToCloneRepositoryException(e.getMessage());
    }
  }
}
