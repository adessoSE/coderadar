package io.reflectoring.coderadar.core.query.port.driven;

import io.reflectoring.coderadar.core.analyzer.domain.Commit;
import java.util.List;

public interface GetCommitsInProjectPort {
  List<Commit> get(Long projectId);
}
