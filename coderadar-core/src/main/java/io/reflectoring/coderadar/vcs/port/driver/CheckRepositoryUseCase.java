package io.reflectoring.coderadar.vcs.port.driver;

import java.nio.file.Path;

public interface CheckRepositoryUseCase {
  boolean isRepository(Path folder);
}
