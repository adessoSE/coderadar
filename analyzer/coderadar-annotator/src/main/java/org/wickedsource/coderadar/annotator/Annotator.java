package org.wickedsource.coderadar.annotator;

import org.eclipse.jgit.api.Git;
import org.wickedsource.coderadar.annotator.clone.GitRepositoryCloner;
import org.wickedsource.coderadar.annotator.clone.RepositoryCloner;
import org.wickedsource.coderadar.annotator.clone.SvnRepositoryCloner;

import java.io.File;
import java.util.Properties;

/**
 * Controls the workflow of cloning a git repository, walking through it, analyzing the files on the way and annotating
 * the analysis results as notes into the git repository.
 */
public class Annotator {
    private final Properties properties;
    private final VcsType vcsType;
    private final String repositoryUrl;
    private final File localRepositoryFolder;

    /*
     * parameters:
     * - properties containing configuration for analyzers
     * - url of remote vcs repository
     * - type of vcs (git or svn)
     * - path to local repository
     *
     * later:
     * - update mode ("incremental" or "complete")
     * - complete mode: number of commits to clone locally
     * - push metrics to remote repository
     * -
     */

    public enum VcsType {
        SVN,
        GIT;
    }

    public Annotator(Properties properties, VcsType vcsType, String repositoryUrl, File localRepositoryFolder) {
        this.properties = properties;
        this.vcsType = vcsType;
        this.repositoryUrl = repositoryUrl;
        this.localRepositoryFolder = localRepositoryFolder;
    }

    public void annotate() {
        Git gitClient = cloneRepository();
        annotateRepository(gitClient);
    }

    private Git cloneRepository() {
        RepositoryCloner cloner;
        if (vcsType == VcsType.SVN) {
            cloner = new SvnRepositoryCloner();
        } else {
            cloner = new GitRepositoryCloner();
        }
        return cloner.cloneRepository(repositoryUrl, localRepositoryFolder);
    }

    private void annotateRepository(Git gitClient){
//        AllCommitsWalker walker = new AllCommitsWalker();
//        walker.walk(gitClient, );
    }

}
