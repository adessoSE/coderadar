package io.reflectoring.coderadar.vcs.port.driver;

import io.reflectoring.coderadar.vcs.domain.VcsCommit;

import java.nio.file.Path;

public interface FindCommitUseCase {
  VcsCommit findCommit(Path repositoryRoot, String name);
}
