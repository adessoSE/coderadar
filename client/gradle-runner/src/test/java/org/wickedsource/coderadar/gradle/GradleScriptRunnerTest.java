package org.wickedsource.coderadar.gradle;

import java.nio.file.Paths;

public class GradleScriptRunnerTest {

    public static void main(String[] args) throws Exception {
        GradleScriptRunner runner = new GradleScriptRunner();
        runner.run(Paths.get("D:/data/workspaces/coderadar"), "clean build");
    }

}