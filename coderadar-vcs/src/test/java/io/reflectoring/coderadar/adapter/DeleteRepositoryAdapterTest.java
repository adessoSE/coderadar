package io.reflectoring.coderadar.adapter;

import io.reflectoring.coderadar.vcs.adapter.DeleteRepositoryAdapter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class DeleteRepositoryAdapterTest {

  @TempDir public File folder;

  private URL testRepoURL = this.getClass().getClassLoader().getResource("test-repository");

  @BeforeEach
  public void setUp() throws GitAPIException {
    Git git = Git.cloneRepository().setURI(testRepoURL.toString()).setDirectory(folder).call();
    git.close();
  }

  @Test
  public void testRepositoryDelete() throws IOException {
    DeleteRepositoryAdapter deleteRepositoryAdapter = new DeleteRepositoryAdapter();

    deleteRepositoryAdapter.deleteRepository(folder.getPath());
    Assertions.assertNull(folder.list());
  }
}
