package io.reflectoring.coderadar.query.service;

import io.reflectoring.coderadar.domain.CommitResponse;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.query.port.driven.GetCommitsInProjectPort;
import io.reflectoring.coderadar.query.port.driver.GetCommitsInProjectUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCommitsInProjectService implements GetCommitsInProjectUseCase {
  private final GetCommitsInProjectPort getCommitsInProjectPort;
  private final GetProjectPort getProjectPort;

  @Override
  public CommitResponse[] get(long projectId, String branch) {
    if (getProjectPort.existsById(projectId)) {
      return getCommitsInProjectPort.getCommitResponses(projectId, branch);
    } else {
      throw new ProjectNotFoundException(projectId);
    }
  }

  @Override
  public CommitResponse[] getForContributor(long projectId, String branchName, String email) {
    if (getProjectPort.existsById(projectId)) {
      return getCommitsInProjectPort.getCommitsForContributorResponses(
          projectId, branchName, email);
    } else {
      throw new ProjectNotFoundException(projectId);
    }
  }
}
