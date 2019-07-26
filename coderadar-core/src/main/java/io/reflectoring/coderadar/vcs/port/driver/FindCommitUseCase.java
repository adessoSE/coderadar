package io.reflectoring.coderadar.vcs.port.driver;

import io.reflectoring.coderadar.analyzer.domain.Commit;

import java.nio.file.Path;

public interface FindCommitUseCase {
  Commit findCommit(Path repositoryRoot, String name);
}
