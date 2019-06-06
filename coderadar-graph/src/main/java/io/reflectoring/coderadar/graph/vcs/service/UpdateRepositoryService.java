package io.reflectoring.coderadar.graph.vcs.service;

import io.reflectoring.coderadar.vcs.port.driven.UpdateRepositoryPort;
import java.nio.file.Path;
import org.springframework.stereotype.Service;

@Service("UpdateRepositoryServiceNeo4j")
public class UpdateRepositoryService implements UpdateRepositoryPort {
  @Override
  public void updateRepository(Path repositoryRoot) {}
}
