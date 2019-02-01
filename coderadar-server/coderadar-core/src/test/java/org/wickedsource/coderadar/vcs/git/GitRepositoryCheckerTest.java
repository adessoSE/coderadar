package org.wickedsource.coderadar.vcs.git;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import org.junit.Assert;
import org.junit.Test;
import org.wickedsource.coderadar.testframework.template.GitTestTemplate;

public class GitRepositoryCheckerTest extends GitTestTemplate {

	@Test
	public void checkRepositorySuccessfully() throws Exception {
		File repoFolder = initRepo().getParentFile();
		GitRepositoryChecker checker = new GitRepositoryChecker();
		Assert.assertTrue(checker.isRepository(repoFolder.toPath()));
	}

	@Test
	public void checkRepositoryAndFail() {
		String tmpDir = System.getProperty("java.io.tmpdir");
		assertNotNull("java.io.tmpdir was null", tmpDir);
		File dir = new File(tmpDir, "git-test-case-" + System.nanoTime());
		assertTrue(dir.mkdir());
		GitRepositoryChecker checker = new GitRepositoryChecker();
		Assert.assertFalse(checker.isRepository(dir.toPath()));
	}
}
