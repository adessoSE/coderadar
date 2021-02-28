package io.reflectoring.coderadar.rest;

import io.reflectoring.coderadar.projectadministration.LongToHashMapper;
import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.query.domain.CommitResponse;
import java.util.ArrayList;
import java.util.List;

public class CommitResponseMapper {
  private CommitResponseMapper() {}

  public static List<CommitResponse> mapCommits(List<Commit> commits) {
    List<CommitResponse> result = new ArrayList<>(commits.size());
    for (Commit commit : commits) {
      result.add(
          new CommitResponse(
              LongToHashMapper.longToHash(commit.getHash()),
              commit.getTimestamp(),
              commit.isAnalyzed(),
              commit.getAuthor(),
              commit.getAuthorEmail(),
              commit.getComment()));
    }
    return result;
  }
}
