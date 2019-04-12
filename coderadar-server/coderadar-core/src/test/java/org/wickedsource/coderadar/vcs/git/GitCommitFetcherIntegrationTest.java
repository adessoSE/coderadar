package org.wickedsource.coderadar.vcs.git;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wickedsource.coderadar.projectadministration.domain.VcsCoordinates;

public class GitCommitFetcherIntegrationTest {

  private Logger logger = LoggerFactory.getLogger(GitCommitFetcherIntegrationTest.class);

  private File tempDir;

  @BeforeEach
  public void setup() {
    String tmpDirString = System.getProperty("java.io.tmpdir");
    Assertions.assertNotNull("java.io.tmpdir was null", tmpDirString);
    tempDir = new File(tmpDirString, "GitCommitFetcherIntegrationTest" + System.nanoTime());
    Assertions.assertTrue(tempDir.mkdir());
  }

  @AfterEach
  public void cleanup() {
    try {
      FileUtils.deleteDirectory(tempDir);
    } catch (IOException e) {
      logger.warn(
          String.format("could not clean up temp dir %s after test due to IOException!", tempDir),
          e);
    }
  }

  @Test
  @Tag("IntegrationTest")
  public void fetchCommit() throws MalformedURLException {
    VcsCoordinates vcs = new VcsCoordinates(new URL("https://github.com/thombergs/diffparser.git"));
    GitCommitFetcher fetcher = new GitCommitFetcher();
    fetcher.fetchCommit("729fa5085a8c40afc100592da98df86b356088a1", vcs, tempDir.toPath());

    File licenseFile = new File(tempDir, "LICENSE");
    Assertions.assertTrue(licenseFile.exists());
  }
}
