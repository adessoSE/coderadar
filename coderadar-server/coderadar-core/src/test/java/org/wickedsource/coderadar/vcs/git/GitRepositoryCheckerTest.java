package org.wickedsource.coderadar.vcs.git;

import java.io.File;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.wickedsource.coderadar.testframework.template.GitTestTemplate;

public class GitRepositoryCheckerTest extends GitTestTemplate {

  @Test
  public void checkRepositorySuccessfully() throws Exception {
    File repoFolder = initRepo().getParentFile();
    GitRepositoryChecker checker = new GitRepositoryChecker();
    Assertions.assertTrue(checker.isRepository(repoFolder.toPath()));
  }

  @Test
  public void checkRepositoryAndFail() {
    String tmpDir = System.getProperty("java.io.tmpdir");
    Assertions.assertNotNull("java.io.tmpdir was null", tmpDir);
    File dir = new File(tmpDir, "git-test-case-" + System.nanoTime());
    Assertions.assertTrue(dir.mkdir());
    GitRepositoryChecker checker = new GitRepositoryChecker();
    Assertions.assertFalse(checker.isRepository(dir.toPath()));
  }
}
