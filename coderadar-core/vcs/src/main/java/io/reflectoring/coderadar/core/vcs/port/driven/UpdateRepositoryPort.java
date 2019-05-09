package io.reflectoring.coderadar.core.vcs.port.driven;

import java.nio.file.Path;

public interface UpdateRepositoryPort {
  void updateRepository(Path repositoryRoot);
}
