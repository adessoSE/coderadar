package io.reflectoring.coderadar.vcs.port.driven;

import io.reflectoring.coderadar.analyzer.domain.Commit;
import java.nio.file.Path;

public interface FindCommitPort {
  Commit findCommit(Path repositoryRoot, String name);
}
