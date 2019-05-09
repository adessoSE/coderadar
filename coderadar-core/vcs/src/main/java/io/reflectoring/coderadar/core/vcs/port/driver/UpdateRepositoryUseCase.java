package io.reflectoring.coderadar.core.vcs.port.driver;

import org.eclipse.jgit.api.Git;

import java.nio.file.Path;

public interface UpdateRepositoryUseCase {
    Git updateRepository(Path repositoryRoot);
}
