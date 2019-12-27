package io.reflectoring.coderadar.vcs.port.driver;

import java.io.IOException;

public interface DeleteRepositoryUseCase {
  void deleteRepository(String repositoryPath) throws IOException;
}
