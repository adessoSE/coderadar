package io.reflectoring.coderadar.vcs.port.driver;

import org.eclipse.jgit.api.Git;

public interface CloneRepositoryUseCase {
  Git cloneRepository(CloneRepositoryCommand command);
}
