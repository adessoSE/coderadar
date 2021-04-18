package io.reflectoring.coderadar.graph;

import io.reflectoring.coderadar.domain.Branch;
import io.reflectoring.coderadar.graph.projectadministration.branch.BranchMapper;
import io.reflectoring.coderadar.graph.projectadministration.domain.BranchEntity;
import io.reflectoring.coderadar.projectadministration.LongToHashMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BranchMapperTest {
  private final BranchMapper branchMapper = new BranchMapper();

  @Test
  void testMapDomainObject() {
    Branch testBranch = new Branch("testName", 1, true);

    BranchEntity result = branchMapper.mapDomainObject(testBranch);
    Assertions.assertEquals(
        "0000000000000001", LongToHashMapper.longToHash(result.getCommitHash()));
    Assertions.assertEquals("testName", result.getName());
    Assertions.assertTrue(result.isTag());
    Assertions.assertNull(result.getId());
  }

  @Test
  void testMapGraphObject() {
    BranchEntity testBranch =
        new BranchEntity().setId(1L).setCommitHash(1).setName("testName").setTag(true);

    Branch result = branchMapper.mapGraphObject(testBranch);
    Assertions.assertEquals(
        "0000000000000001", LongToHashMapper.longToHash(result.getCommitHash()));
    Assertions.assertEquals("testName", result.getName());
    Assertions.assertTrue(result.isTag());
  }
}
