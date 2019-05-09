package io.reflectoring.coderadar.core.vcs.port.driver;

import org.eclipse.jgit.api.Git;

import java.nio.file.Path;

public interface ResetRepositoryUseCase {
  Git resetRepository(Path repositoryRoot);
}
