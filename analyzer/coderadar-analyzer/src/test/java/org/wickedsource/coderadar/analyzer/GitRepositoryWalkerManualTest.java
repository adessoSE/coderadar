package org.wickedsource.coderadar.analyzer;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;

public class GitRepositoryWalkerManualTest {

    public static void main(String[] args) throws IOException, GitAPIException {
        Git git = Git.open(new File("D:\\Test\\trunk"));
        GitCommitWalker walker = new GitCommitWalker(git);
        walker.walkCommits();
    }
}
