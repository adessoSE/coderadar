package io.reflectoring.coderadar.graph;

import io.reflectoring.coderadar.graph.projectadministration.branch.BranchMapper;
import io.reflectoring.coderadar.graph.projectadministration.domain.BranchEntity;
import io.reflectoring.coderadar.projectadministration.domain.Branch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BranchMapperTest {
  private final BranchMapper branchMapper = new BranchMapper();

  @Test
  void testMapDomainObject() {
    Branch testBranch = new Branch().setCommitHash("testHash").setName("testName");

    BranchEntity result = branchMapper.mapDomainObject(testBranch);
    Assertions.assertEquals("testHash", result.getCommitHash());
    Assertions.assertEquals("testName", result.getName());
    Assertions.assertNull(result.getId());
  }

  @Test
  void testMapGraphObject() {
    BranchEntity testBranch =
        new BranchEntity().setId(1L).setCommitHash("testHash").setName("testName");

    Branch result = branchMapper.mapGraphObject(testBranch);
    Assertions.assertEquals("testHash", result.getCommitHash());
    Assertions.assertEquals("testName", result.getName());
  }
}
