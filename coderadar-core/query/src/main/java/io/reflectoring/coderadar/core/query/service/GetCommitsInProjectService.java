package io.reflectoring.coderadar.core.query.service;

import io.reflectoring.coderadar.core.projectadministration.domain.Commit;
import io.reflectoring.coderadar.core.query.port.driven.GetCommitsInProjectPort;
import io.reflectoring.coderadar.core.query.port.driver.GetCommitResponse;
import io.reflectoring.coderadar.core.query.port.driver.GetCommitsInProjectUseCase;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("GetCommitsInProjectService")
public class GetCommitsInProjectService implements GetCommitsInProjectUseCase {
  private final GetCommitsInProjectPort getCommitsInProjectPort;

  @Autowired
  public GetCommitsInProjectService(
      @Qualifier("GetCommitsInProjectServiceNeo4j")
          GetCommitsInProjectPort getCommitsInProjectPort) {
    this.getCommitsInProjectPort = getCommitsInProjectPort;
  }

  @Override
  public List<GetCommitResponse> get(Long projectId) {
    List<GetCommitResponse> response = new ArrayList<>();
    for (Commit commit : getCommitsInProjectPort.get(projectId)) {
      GetCommitResponse getCommitResponse = new GetCommitResponse();
      getCommitResponse.setName(commit.getName());
      getCommitResponse.setAuthor(commit.getAuthor());
      getCommitResponse.setComment(commit.getComment());
      getCommitResponse.setTimestamp(commit.getTimestamp().toString());
      getCommitResponse.setAnalyzed(commit.isAnalyzed());
      response.add(getCommitResponse);
    }
    return response;
  }
}
