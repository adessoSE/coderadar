package io.reflectoring.coderadar.adapter;

import io.reflectoring.coderadar.query.domain.CommitLog;
import io.reflectoring.coderadar.vcs.adapter.GetCommitLogAdapter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class GetCommitLogAdapterTest {

  @TempDir public File folder;

  private URL testRepoURL = this.getClass().getClassLoader().getResource("test-repository");

  @BeforeEach
  public void setUp() throws GitAPIException {
    Git git = Git.cloneRepository().setURI(testRepoURL.toString()).setDirectory(folder).call();
    git.close();
  }

  @AfterEach
  public void tearDown() throws IOException {
    FileUtils.deleteDirectory(folder);
  }

  @Test
  public void testGetCommitLog() {
    GetCommitLogAdapter getCommitLogAdapter = new GetCommitLogAdapter();
    List<CommitLog> commitLogs = getCommitLogAdapter.getCommitLog(folder.getPath());
    Assertions.assertEquals(14, commitLogs.size());

    CommitLog last = commitLogs.get(0);
    Assertions.assertEquals("e9f7ff6fdd8c0863fdb5b24c9ed35a3651e20382", last.getHash());
    Assertions.assertEquals("modify testModule1/NewRandomFile.java", last.getSubject());
    Assertions.assertEquals(Arrays.asList("HEAD", "master"), last.getRefs());
    Assertions.assertEquals(
        Collections.singletonList("d3272b3793bc4b2bc36a1a3a7c8293fcf8fe27df"), last.getParents());
    Assertions.assertEquals("Kilian.Krause@adesso.de", last.getAuthor().getEmail());
    Assertions.assertEquals("Krause", last.getAuthor().getName());
    Assertions.assertEquals(1584013941000L, last.getAuthor().getTimestamp());

    CommitLog secondToLast = commitLogs.get(1);
    Assertions.assertEquals("d3272b3793bc4b2bc36a1a3a7c8293fcf8fe27df", secondToLast.getHash());
    Assertions.assertEquals("testCommit", secondToLast.getSubject());
    Assertions.assertEquals(Collections.emptyList(), secondToLast.getRefs());
    Assertions.assertEquals(
        Collections.singletonList("251dc2fde2db8d9acaedede2bcf742e0f32c128b"),
        secondToLast.getParents());
    Assertions.assertEquals("Maksim.Atanasov@adesso.de", secondToLast.getAuthor().getEmail());
    Assertions.assertEquals("maximAtanasov", secondToLast.getAuthor().getName());
    Assertions.assertEquals(1565615004000L, secondToLast.getAuthor().getTimestamp());
  }

  @Test
  public void testGetCommitLogAdapterThrowsWhenRepositoryDoesNotExist() {
    GetCommitLogAdapter getCommitLogAdapter = new GetCommitLogAdapter();
    Assertions.assertThrows(
        IllegalStateException.class, () -> getCommitLogAdapter.getCommitLog("./non/existent/path"));
  }
}
