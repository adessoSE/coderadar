package io.reflectoring.coderadar.rest;

import io.reflectoring.coderadar.domain.Branch;
import io.reflectoring.coderadar.rest.domain.GetBranchResponse;
import java.util.ArrayList;
import java.util.List;

public class GetBranchResponseMapper {
  private GetBranchResponseMapper() {}

  public static List<GetBranchResponse> mapBranches(List<Branch> branches) {
    List<GetBranchResponse> result = new ArrayList<>(branches.size());
    for (Branch branch : branches) {
      result.add(new GetBranchResponse(branch.getName(), branch.getCommitHash(), branch.isTag()));
    }
    return result;
  }
}
