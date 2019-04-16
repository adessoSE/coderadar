package io.reflectoring.coderadar.core.query.port.driver;

import io.reflectoring.coderadar.core.analyzer.domain.Commit;
import java.util.List;

public interface GetCommitsInProjectUseCase {
  List<Commit> get(Long projectId);
}
