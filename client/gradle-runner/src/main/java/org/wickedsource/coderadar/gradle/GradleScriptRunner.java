package org.wickedsource.coderadar.gradle;


import org.apache.commons.exec.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

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

    public List<File> getCompileDependencies(Path workDir) throws IOException {

        Path buildFile = workDir.resolve("build.gradle");
        if(!hasPrintClasspathTask(buildFile)){
            addPrintClasspathTask(buildFile);
        }

        File gradleExecutable = new File(workDir.toFile(), "gradlew.bat");
        CommandLine commandLine = new CommandLine(gradleExecutable.getAbsoluteFile());
        commandLine.addArguments("printClassPath");
        DefaultExecutor executor = new DefaultExecutor();
        List<File> jarFiles = new ArrayList<>();
        LogOutputStream out = new LogOutputStream() {
            @Override
            protected void processLine(String line, int logLevel) {
                jarFiles.add(new File(line));
            }
        };
        PumpStreamHandler handler = new PumpStreamHandler(out);
        executor.setWorkingDirectory(workDir.toFile());
        executor.setStreamHandler(handler);
        executor.execute(commandLine);
        return jarFiles;
    }

    private void addPrintClasspathTask(Path buildFile) throws IOException {
        String printClassPathTask = "\n" +
                "// this task has been automatically added by coderadar to access all JAR dependencies for bytecode analysis\n" +
                "task printClasspathForCoderadar {\n" +
                "    doLast {\n" +
                "        configurations.testRuntime.each { println it }\n" +
                "    }\n" +
                "}";

        Files.write(buildFile, printClassPathTask.getBytes(), StandardOpenOption.APPEND);
    }

    private boolean hasPrintClasspathTask(Path buildFile) throws IOException {
        List<String> lines = Files.readAllLines(buildFile);
        for(String line : lines){
            if(line.startsWith("// this task has been automatically added by coderadar to access all JAR dependencies for bytecode analysis")){
                return true;
            }
        }
        return false;
    }

}
