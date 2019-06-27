package io.reflectoring.coderadar.vcs.adapter;

import io.reflectoring.coderadar.vcs.port.driven.CheckRepositoryPort;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;

@Service
public class CheckRepositoryAdapter implements CheckRepositoryPort {

  @Override
  public boolean isRepository(Path folder) {
    try {
      FileRepositoryBuilder builder = new FileRepositoryBuilder();
      Repository repository = builder.setWorkTree(folder.toFile()).build();
      return repository.getObjectDatabase().exists();
    } catch (IOException e) {
      return false;
    }
  }
}
