package org.wickedsource.coderadar.analyzer;

import org.eclipse.jgit.api.Git;
import org.wickedsource.coderadar.analyzer.clone.SvnRepositoryCloner;

import java.io.File;

public class SvnRepositoryClonerManualTest {

    public static void main(String[] args){
        SvnRepositoryCloner cloner = new SvnRepositoryCloner();
        Git git = cloner.cloneRepository("http://wickethtml5.googlecode.com/svn/trunk", new File("D:\\Test"));
        GitCommitWalker walker = new GitCommitWalker(git);
        walker.walkCommits();
    }
}
