package io.reflectoring.coderadar.vcs.service;

import io.reflectoring.coderadar.vcs.port.driver.CheckRepositoryUseCase;
import java.io.IOException;
import java.nio.file.Path;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CheckRepositoryService implements CheckRepositoryUseCase {

  private Logger logger = LoggerFactory.getLogger(CheckRepositoryService.class);

  @Override
  public boolean isRepository(Path folder) {
    try {
      FileRepositoryBuilder builder = new FileRepositoryBuilder();
      Repository repository = builder.setWorkTree(folder.toFile()).build();
      return repository.getObjectDatabase().exists();
    } catch (IOException e) {
      logger.warn("Exception when checking local GIT repository!", e);
      return false;
    }
  }
}
