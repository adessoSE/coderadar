package io.reflectoring.coderadar.adapter;

import io.reflectoring.coderadar.vcs.UnableToCloneRepositoryException;
import io.reflectoring.coderadar.vcs.adapter.CloneRepositoryAdapter;
import java.net.URL;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class CloneRepositoryAdapterTest {

  @Rule public TemporaryFolder folder = new TemporaryFolder();

  @Test
  public void test() throws UnableToCloneRepositoryException {
    URL testRepoURL = this.getClass().getClassLoader().getResource("test-repository");

    CloneRepositoryAdapter cloneRepositoryAdapter = new CloneRepositoryAdapter();
    cloneRepositoryAdapter.cloneRepository(testRepoURL.toString(), folder.getRoot());

    Assert.assertEquals(3, folder.getRoot().list().length);
    Assert.assertEquals(".git", folder.getRoot().list()[0]);
    Assert.assertEquals("GetMetricsForCommitCommand.java", folder.getRoot().list()[1]);
    Assert.assertEquals("testModule1", folder.getRoot().list()[2]);
  }
}
