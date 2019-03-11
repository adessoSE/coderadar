package org.wickedsource.coderadar.testframework.template;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.io.File;
import org.springframework.test.annotation.DirtiesContext;
import org.wickedsource.coderadar.core.WorkdirManager;
import org.wickedsource.coderadar.core.configuration.CoderadarConfiguration;

@DirtiesContext
public class TestTemplate {

  protected void mock(WorkdirManager workdirManagerMock) {
    when(workdirManagerMock.getLocalGitRoot(anyLong())).thenReturn(createTempDir().toPath());
  }

  protected void mock(CoderadarConfiguration configMock) {
    when(configMock.getScanIntervalInSeconds()).thenReturn(30);
    when(configMock.getWorkdir()).thenReturn(createTempDir().toPath());
    when(configMock.isMaster()).thenReturn(true);
    when(configMock.isSlave()).thenReturn(false);
  }

  protected File createTempDir() {
    String tmpDir = System.getProperty("java.io.tmpdir");
    assertNotNull("java.io.tmpdir was null", tmpDir);
    File dir = new File(tmpDir, "test-case-" + System.nanoTime());
    assertTrue(dir.mkdir());
    return dir;
  }
}
