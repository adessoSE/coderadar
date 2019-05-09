package io.reflectoring.coderadar.core.vcs.port.driver;

import java.nio.file.Path;
import org.eclipse.jgit.api.Git;

public interface ResetRepositoryUseCase {
  Git resetRepository(Path repositoryRoot);
}
