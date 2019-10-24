package io.reflectoring.coderadar.adapter;

import io.reflectoring.coderadar.vcs.adapter.DeleteRepositoryAdapter;
import java.io.IOException;
import java.net.URL;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class DeleteRepositoryAdapterTest {

  @Rule public TemporaryFolder folder = new TemporaryFolder();

  private URL testRepoURL = this.getClass().getClassLoader().getResource("test-repository");

  @Before
  public void setUp() throws GitAPIException {
    Git git =
        Git.cloneRepository().setURI(testRepoURL.toString()).setDirectory(folder.getRoot()).call();
    git.close();
  }

  @Test
  public void testRepositoryDelete() throws IOException {
    DeleteRepositoryAdapter deleteRepositoryAdapter = new DeleteRepositoryAdapter();

    deleteRepositoryAdapter.deleteRepository(folder.getRoot().getPath());
    Assert.assertNull(folder.getRoot().list());
  }
}
