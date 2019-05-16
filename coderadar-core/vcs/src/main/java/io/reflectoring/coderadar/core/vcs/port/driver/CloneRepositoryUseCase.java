package io.reflectoring.coderadar.core.vcs.port.driver;

import org.eclipse.jgit.api.Git;

public interface CloneRepositoryUseCase {
  Git cloneRepository(CloneRepositoryCommand command);
}
