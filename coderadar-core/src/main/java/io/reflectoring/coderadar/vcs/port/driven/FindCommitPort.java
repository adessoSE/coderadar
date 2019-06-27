package io.reflectoring.coderadar.vcs.port.driven;

import io.reflectoring.coderadar.vcs.domain.VcsCommit;

import java.nio.file.Path;

public interface FindCommitPort {
  VcsCommit findCommit(Path repositoryRoot, String name);
}
