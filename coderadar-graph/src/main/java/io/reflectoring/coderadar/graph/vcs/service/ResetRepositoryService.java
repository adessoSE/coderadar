package io.reflectoring.coderadar.graph.vcs.service;

import io.reflectoring.coderadar.core.vcs.port.driven.ResetRepositoryPort;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service("ResetRepositoryServiceNeo4j")
public class ResetRepositoryService implements ResetRepositoryPort{
    @Override
    public void resetRepository(Path repositoryRoot) {
    }
}
