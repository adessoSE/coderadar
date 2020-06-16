package io.reflectoring.coderadar.rest.unit;

import io.reflectoring.coderadar.projectadministration.domain.Branch;
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
    branches.add(new Branch("testBranch1", "testHash1"));
    branches.add(new Branch("testBranch2", "testHash2"));
    branches.add(new Branch("testBranch3", "testHash3"));

    List<GetBranchResponse> responses = GetBranchResponseMapper.mapBranches(branches);
    Assertions.assertEquals(3L, responses.size());
    Assertions.assertEquals("testBranch1", responses.get(0).getName());
    Assertions.assertEquals("testHash1", responses.get(0).getCommitHash());
    Assertions.assertEquals("testBranch2", responses.get(1).getName());
    Assertions.assertEquals("testHash2", responses.get(1).getCommitHash());
    Assertions.assertEquals("testBranch3", responses.get(2).getName());
    Assertions.assertEquals("testHash3", responses.get(2).getCommitHash());
  }
}
