package org.wickedsource.coderadar.vcs;

import org.eclipse.jgit.api.Git;

import java.nio.file.Path;

public interface RepositoryUpdater {

    /**
     * Updates the local repository to the most current commit.
     *
     * @return the git client to the local GIT repository
     */
    Git updateRepository(Path repositoryRoot);

}
