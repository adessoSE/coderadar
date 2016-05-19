package org.wickedsource.coderadar.vcs.git;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wickedsource.coderadar.IntegrationTest;
import org.wickedsource.coderadar.project.domain.VcsCoordinates;
import org.wickedsource.coderadar.project.domain.VcsType;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class GitCommitFetcherIntegrationTest {

    private Logger logger = LoggerFactory.getLogger(GitCommitFetcherIntegrationTest.class);

    private File tempDir;

    @Before
    public void setup() {
        String tmpDirString = System.getProperty("java.io.tmpdir");
        assertNotNull("java.io.tmpdir was null", tmpDirString);
        tempDir = new File(tmpDirString, "GitCommitFetcherIntegrationTest" + System.nanoTime());
        assertTrue(tempDir.mkdir());
    }

    @After
    public void cleanup() {
        try {
            FileUtils.deleteDirectory(tempDir);
        } catch (IOException e) {
            logger.warn(String.format("could not clean up temp dir %s after test due to IOException!", tempDir), e);
        }
    }

    @Test
    @Category(IntegrationTest.class)
    public void fetchFirstCoderadarCommit() throws MalformedURLException {
        VcsCoordinates vcs = new VcsCoordinates(new URL("https://github.com/thombergs/coderadar.git"), VcsType.GIT);
        GitCommitFetcher fetcher = new GitCommitFetcher();
        fetcher.fetchCommit("5412226262a0968ddf8a98d02f34c50dd91b7f5a", vcs, tempDir.toPath());

        File licenseFile = new File(tempDir, "LICENSE");
        Assert.assertTrue(licenseFile.exists());
    }

}