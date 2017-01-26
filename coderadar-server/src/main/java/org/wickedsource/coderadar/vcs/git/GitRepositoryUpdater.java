package org.wickedsource.coderadar.vcs.git;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.vcs.RepositoryUpdater;

import java.io.IOException;
import java.nio.file.Path;

@Service
public class GitRepositoryUpdater implements RepositoryUpdater {

    private GitRepositoryReverter reverter;

    @Autowired
    public GitRepositoryUpdater(GitRepositoryReverter reverter) {
        this.reverter = reverter;
    }

    @Override
    public Git updateRepository(Path repositoryRoot) {
        try {
            return updateInternal(repositoryRoot);
        } catch (CheckoutConflictException e) {
            // When having a checkout conflict, someone or something fiddled with the working directory.
            // Since the working directory is designed to be read only, we just revert it and try again.
            reverter.revertRepository(repositoryRoot);
            try {
                return updateInternal(repositoryRoot);
            } catch (Exception e2) {
                throw createException(e2, repositoryRoot);
            }
        } catch (Exception e) {
            throw createException(e, repositoryRoot);
        }
    }

    private IllegalStateException createException(Exception cause, Path repositoryRoot) {
        return new IllegalStateException(
                String.format("error accessing local GIT repository at %s", repositoryRoot), cause);
    }

    private Git updateInternal(Path repositoryRoot) throws GitAPIException, IOException {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repository = builder.setWorkTree(repositoryRoot.toFile()).build();
        Git git = new Git(repository);
        git.pull().setStrategy(MergeStrategy.THEIRS).call();
        return git;
    }
}
