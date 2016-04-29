package org.wickedsource.coderadar.gradle;


import org.apache.commons.exec.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class GradleScriptRunner {

    private Logger logger = LoggerFactory.getLogger(GradleScriptRunner.class);

    public void run(Path workDir, String arguments) throws IOException {
        File gradleExecutable = new File(workDir.toFile(), "gradlew.bat");
        CommandLine commandLine = new CommandLine(gradleExecutable.getAbsoluteFile());
        commandLine.addArguments(arguments);
        DefaultExecutor executor = new DefaultExecutor();
        LogOutputStream out = new LogOutputStream() {
            @Override
            protected void processLine(String line, int logLevel) {
                logger.info(line);
            }
        };
        PumpStreamHandler handler = new PumpStreamHandler(out);
        executor.setWorkingDirectory(workDir.toFile());
        executor.setStreamHandler(handler);
        executor.execute(commandLine);
    }

}
