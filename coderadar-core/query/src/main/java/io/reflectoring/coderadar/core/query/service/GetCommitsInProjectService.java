package io.reflectoring.coderadar.core.query.service;

import io.reflectoring.coderadar.core.analyzer.domain.Commit;
import io.reflectoring.coderadar.core.query.port.driven.GetCommitsInProjectPort;
import io.reflectoring.coderadar.core.query.port.driver.GetCommitsInProjectUseCase;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("GetCommitsInProjectService")
public class GetCommitsInProjectService implements GetCommitsInProjectUseCase {
  private final GetCommitsInProjectPort getCommitsInProjectPort;

  @Autowired
  public GetCommitsInProjectService(GetCommitsInProjectPort getCommitsInProjectPort) {
    this.getCommitsInProjectPort = getCommitsInProjectPort;
  }

  @Override
  public List<Commit> get(Long projectId) {
    return getCommitsInProjectPort.get(projectId);
  }
}
