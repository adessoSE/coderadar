package io.reflectoring.coderadar.vcs.port.driven;

import java.io.IOException;

public interface DeleteRepositoryPort {
  void deleteRepository(String repositoryPath) throws IOException;
}
