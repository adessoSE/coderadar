package io.reflectoring.coderadar.adapter;

import io.reflectoring.coderadar.vcs.UnableToCloneRepositoryException;
import io.reflectoring.coderadar.vcs.adapter.CloneRepositoryAdapter;
import io.reflectoring.coderadar.vcs.port.driver.clone.CloneRepositoryCommand;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class CloneRepositoryAdapterTest {

  @TempDir public File folder;

  @Test
  void test() throws UnableToCloneRepositoryException {
    URL testRepoURL = this.getClass().getClassLoader().getResource("test-repository");

    CloneRepositoryAdapter cloneRepositoryAdapter = new CloneRepositoryAdapter();
    cloneRepositoryAdapter.cloneRepository(
        new CloneRepositoryCommand(testRepoURL.toString(), folder.toString(), "", ""));

    Assertions.assertEquals(8, folder.list().length);
    Assertions.assertTrue(Arrays.asList(folder.list()).contains("branches"));
    Assertions.assertTrue(Arrays.asList(folder.list()).contains("config"));
    Assertions.assertTrue(Arrays.asList(folder.list()).contains("HEAD"));
    Assertions.assertTrue(Arrays.asList(folder.list()).contains("hooks"));
    Assertions.assertTrue(Arrays.asList(folder.list()).contains("logs"));
    Assertions.assertTrue(Arrays.asList(folder.list()).contains("objects"));
  }

  @AfterEach
  public void tearDown() throws IOException {
    FileUtils.deleteDirectory(folder);
  }
}
