package org.wickedsource.coderadar.gradle;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

public class GradleScriptRunnerTest {

    public static void main(String[] args) throws Exception {
        GradleScriptRunner runner = new GradleScriptRunner();
//        runner.run(Paths.get("D:/data/workspaces/coderadar"), "clean build");

        List<File> files = runner.getCompileDependencies(Paths.get("D:/data/workspaces/coderadar"));
        for(File file : files){
            System.out.println(file);
        }
    }

}