package io.reflectoring.coderadar.query.port.driver;

import java.util.List;

public interface GetCommitsInProjectUseCase {
  List<GetCommitResponse> get(Long projectId);
}
