package org.wickedsource.coderadar.vcs.git;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.vcs.RepositoryChecker;

import java.io.IOException;
import java.nio.file.Path;

@Service
public class GitRepositoryChecker implements RepositoryChecker {

    private Logger logger = LoggerFactory.getLogger(GitRepositoryChecker.class);

    @Override
    public boolean isRepository(Path folder) {
        try {
            FileRepositoryBuilder builder = new FileRepositoryBuilder();
            Repository repository = builder.setWorkTree(folder.toFile()).build();
            return repository.getObjectDatabase().exists();
        } catch (IOException e) {
            logger.warn("Exception when checking local GIT repository!", e);
            return false;
        }
    }

}
