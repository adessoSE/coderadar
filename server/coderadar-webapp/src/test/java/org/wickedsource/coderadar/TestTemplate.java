package org.wickedsource.coderadar;

import org.wickedsource.coderadar.core.WorkdirManager;

import java.io.File;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class TestTemplate {

    protected void mock(WorkdirManager workdirManagerMock){
        when(workdirManagerMock.getLocalGitRoot(any(Long.class))).thenReturn(createTempDir().toPath());
    }

    protected void mock(CoderadarConfiguration configMock){
        when(configMock.getScanIntervalInSeconds()).thenReturn(30);
        when(configMock.getWorkdir()).thenReturn(createTempDir().toPath());
        when(configMock.isMaster()).thenReturn(true);
        when(configMock.isSlave()).thenReturn(false);
    }

    protected File createTempDir(){
        String tmpDir = System.getProperty("java.io.tmpdir");
        assertNotNull("java.io.tmpdir was null", tmpDir);
        File dir = new File(tmpDir, "test-case-" + System.nanoTime());
        assertTrue(dir.mkdir());
        return dir;
    }


}
