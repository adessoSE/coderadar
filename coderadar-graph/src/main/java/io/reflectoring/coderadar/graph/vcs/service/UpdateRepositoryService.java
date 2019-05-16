package io.reflectoring.coderadar.graph.vcs.service;

import io.reflectoring.coderadar.core.vcs.port.driven.UpdateRepositoryPort;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service("UpdateRepositoryServiceNeo4j")
public class UpdateRepositoryService implements UpdateRepositoryPort {
    @Override
    public void updateRepository(Path repositoryRoot) {

    }
}
