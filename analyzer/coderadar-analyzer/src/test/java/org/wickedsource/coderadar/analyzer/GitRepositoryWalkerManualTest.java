package org.wickedsource.coderadar.analyzer;

import org.eclipse.jgit.api.Git;

import java.io.File;
import java.io.IOException;

public class GitRepositoryWalkerManualTest {

    public static void main(String[] args) throws IOException {
        Git git = Git.open(new File("D:\\Test\\trunk"));
        GitCommitWalker walker = new GitCommitWalker(git);
        walker.walkCommits();
    }
}
