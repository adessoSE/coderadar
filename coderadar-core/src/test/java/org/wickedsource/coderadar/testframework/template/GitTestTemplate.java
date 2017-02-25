package org.wickedsource.coderadar.testframework.template;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.PrintWriter;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Template for test cases that need a git repository. */
public abstract class GitTestTemplate extends TestTemplate {

  private Logger logger = LoggerFactory.getLogger(GitTestTemplate.class);

  private File testRepo;

  protected Git git;

  private PersonIdent author = new PersonIdent("Test Author", "author@test.com");

  private PersonIdent committer = new PersonIdent("Test Committer", "committer@test.com");

  @Before
  public void setUp() throws Exception {
    testRepo = initRepo();
    git = Git.open(testRepo);
    logger.info("created temporary git repository at {}", testRepo);
  }

  @After
  public void cleanUp() throws Exception {
    git.close();
    FileUtils.deleteDirectory(testRepo.getParentFile());
    logger.info("deleted temporary git repository at {}", testRepo);
  }

  protected File initRepo() throws Exception {
    File dir = createTempDir();

    Git.init().setDirectory(dir).setBare(false).call();
    File repo = new File(dir, Constants.DOT_GIT);
    assertTrue(repo.exists());

    return repo;
  }

  protected void add(String path, String content) throws Exception {
    File file = new File(testRepo.getParentFile(), path);
    if (!file.getParentFile().exists()) assertTrue(file.getParentFile().mkdirs());
    if (!file.exists()) assertTrue(file.createNewFile());
    PrintWriter writer = new PrintWriter(file);
    if (content == null) content = "";
    try {
      writer.print(content);
    } finally {
      writer.close();
    }
    git.add().addFilepattern(path).call();
  }

  protected RevCommit commit() throws Exception {
    RevCommit commit =
        git.commit().setMessage("test commit").setAuthor(author).setCommitter(committer).call();
    return commit;
  }
}
