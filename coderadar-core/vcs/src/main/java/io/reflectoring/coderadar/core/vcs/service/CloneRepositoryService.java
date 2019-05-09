package io.reflectoring.coderadar.core.vcs.service;

import io.reflectoring.coderadar.core.vcs.port.driven.CloneRepositoryPort;
import io.reflectoring.coderadar.core.vcs.port.driver.CloneRepositoryCommand;
import io.reflectoring.coderadar.core.vcs.port.driver.CloneRepositoryUseCase;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CloneRepositoryService implements CloneRepositoryUseCase {

    private final CloneRepositoryPort cloneRepositoryPort;

    @Autowired
    public CloneRepositoryService(CloneRepositoryPort cloneRepositoryPort){
        this.cloneRepositoryPort = cloneRepositoryPort;
    }

    @Override
    public Git cloneRepository(CloneRepositoryCommand command) {
        try {
            // TODO: support cloning with credentials for private repositories
            // TODO: support progress monitoring
            Git git = Git.cloneRepository().setURI(command.getRemoteUrl()).setDirectory(command.getLocalDir()).call();
            git.getRepository().close();
            // TODO: Save repository in graph DB using port
            return git;
        } catch (GitAPIException e) {
            throw new RuntimeException(e);
        }
    }
}
