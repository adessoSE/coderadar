package io.reflectoring.coderadar.projectadministration.port.driven.analyzer;

import io.reflectoring.coderadar.projectadministration.domain.Branch;
import io.reflectoring.coderadar.projectadministration.domain.Commit;
import java.util.List;

public interface AddCommitsPort {

  void addCommits(long id, List<Commit> commits, List<Branch> updatedBranches);
}
