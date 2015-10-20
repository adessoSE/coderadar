package org.wickedsource.coderadar.analyzer.clone;

import org.eclipse.jgit.api.Git;

import java.io.File;

public interface RepositoryCloner {

    /**
     * Clones a remote source code repository into a local git repository.
     *
     * @param remoteUrl the URL at which the remote repository is available.
     * @param localDir  the local directory in which to put the local git repository.
     * @return a JGit Git client.
     */
    public Git cloneRepository(String remoteUrl, File localDir);
}
