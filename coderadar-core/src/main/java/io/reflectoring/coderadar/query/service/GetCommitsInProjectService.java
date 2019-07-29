package io.reflectoring.coderadar.query.service;

import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.query.port.driven.GetCommitsInProjectPort;
import io.reflectoring.coderadar.query.port.driver.GetCommitResponse;
import io.reflectoring.coderadar.query.port.driver.GetCommitsInProjectUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetCommitsInProjectService implements GetCommitsInProjectUseCase {
  private final GetCommitsInProjectPort getCommitsInProjectPort;

  @Autowired
  public GetCommitsInProjectService(GetCommitsInProjectPort getCommitsInProjectPort) {
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
