package org.wickedsource.coderadar.analyzer;

import java.io.File;
import java.io.IOException;

public class GitRepositoryClonerManualTest {

    public static void main(String[] args) throws IOException {
        GitRepositoryCloner cloner = new GitRepositoryCloner();
        File dir = File.createTempFile("GitRepo", "");
        dir.delete();
        cloner.cloneRepository("https://github.com/thombergs/diffparser.git", dir);
        System.out.println(String.format("cloned repository to %s", dir.getAbsolutePath()));
    }

}