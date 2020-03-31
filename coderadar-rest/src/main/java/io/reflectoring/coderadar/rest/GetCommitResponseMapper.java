package io.reflectoring.coderadar.rest;

import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.rest.domain.GetCommitResponse;
import java.util.ArrayList;
import java.util.List;

public class GetCommitResponseMapper {
  private GetCommitResponseMapper() {}

  public static List<GetCommitResponse> mapCommits(List<Commit> commits) {
    List<GetCommitResponse> result = new ArrayList<>(commits.size());
    for (Commit commit : commits) {
      result.add(
          new GetCommitResponse()
              .setName(commit.getName())
              .setAnalyzed(commit.isAnalyzed())
              .setAuthor(commit.getAuthor())
              .setAuthorEmail(commit.getAuthorEmail())
              .setComment(commit.getComment())
              .setTimestamp(commit.getTimestamp()));
    }
    return result;
  }
}
