package org.wickedsource.coderadar.vcs.git;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.vcs.RepositoryCloner;

import java.io.File;

@Service
public class GitRepositoryCloner implements RepositoryCloner {

    public Git cloneRepository(String remoteUrl, File localDir) {
        try {
            // TODO: support cloning with credentials for private repositories
            // TODO: support progress monitoring
            Git git = Git.cloneRepository()
                    .setURI(remoteUrl)
                    .setDirectory(localDir)
                    .call();
            git.getRepository().close();
            return git;
        } catch (GitAPIException e) {
            throw new RuntimeException(e);
        }
    }

}
