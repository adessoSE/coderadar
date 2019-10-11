package io.reflectoring.coderadar.projectadministration.port.driven.analyzer;

import io.reflectoring.coderadar.analyzer.domain.Commit;
import java.util.List;

public interface UpdateCommitsPort {
  void updateCommits(List<Commit> commits, Long id);
}
