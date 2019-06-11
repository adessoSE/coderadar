package io.reflectoring.coderadar.vcs.port.driven;

import java.nio.file.Path;

public interface UpdateRepositoryPort {
  void updateRepository(Path repositoryRoot);
}