package io.reflectoring.coderadar.rest.unit;

import io.reflectoring.coderadar.domain.Branch;
import io.reflectoring.coderadar.projectadministration.LongToHashMapper;
import io.reflectoring.coderadar.rest.GetBranchResponseMapper;
import io.reflectoring.coderadar.rest.domain.GetBranchResponse;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GetBranchResponseMapperTest {

  @Test
  void testBranchResponseMapper() {
    List<Branch> branches = new ArrayList<>();
    branches.add(new Branch("testBranch1", 1, false));
    branches.add(new Branch("testBranch2", 2, false));
    branches.add(new Branch("testBranch3", 3, false));

    List<GetBranchResponse> responses = GetBranchResponseMapper.mapBranches(branches);
    Assertions.assertEquals(3L, responses.size());
    Assertions.assertEquals("testBranch1", responses.get(0).getName());
    Assertions.assertEquals(
        "0000000000000001", LongToHashMapper.longToHash(responses.get(0).getCommitHash()));
    Assertions.assertEquals("testBranch2", responses.get(1).getName());
    Assertions.assertEquals(
        "0000000000000002", LongToHashMapper.longToHash(responses.get(1).getCommitHash()));
    Assertions.assertEquals("testBranch3", responses.get(2).getName());
    Assertions.assertEquals(
        "0000000000000003", LongToHashMapper.longToHash(responses.get(2).getCommitHash()));
  }
}
