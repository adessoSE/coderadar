package io.reflectoring.coderadar.vcs.port.driven;

import java.io.IOException;

public interface DeleteLocalRepositoryPort {
  void deleteRepository(String repositoryPath) throws IOException;
}
