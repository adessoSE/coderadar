package io.reflectoring.coderadar.core.vcs.port.driven;

import java.nio.file.Path;

public interface ResetRepositoryPort {
  void resetRepository(Path repositoryRoot);
}
