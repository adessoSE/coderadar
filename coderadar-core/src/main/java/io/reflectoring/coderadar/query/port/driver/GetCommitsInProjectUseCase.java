package io.reflectoring.coderadar.query.port.driver;

import io.reflectoring.coderadar.projectadministration.domain.Commit;

import java.util.List;

public interface GetCommitsInProjectUseCase {
  List<Commit> get(Long projectId);
}
