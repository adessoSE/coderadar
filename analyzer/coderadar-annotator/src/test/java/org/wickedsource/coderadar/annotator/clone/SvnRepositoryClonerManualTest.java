package org.wickedsource.coderadar.annotator.clone;

import org.eclipse.jgit.api.Git;

import java.io.File;

public class SvnRepositoryClonerManualTest {

    public static void main(String[] args){
        SvnRepositoryCloner cloner = new SvnRepositoryCloner();
        Git git = cloner.cloneRepository("http://wickethtml5.googlecode.com/svn/trunk", new File("D:\\Test"));
    }
}
