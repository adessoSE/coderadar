package io.reflectoring.coderadar.graph.vcs.service;

import io.reflectoring.coderadar.vcs.port.driven.ResetRepositoryPort;
import java.nio.file.Path;
import org.springframework.stereotype.Service;

@Service
public class ResetRepositoryAdapter implements ResetRepositoryPort {
  @Override
  public void resetRepository(Path repositoryRoot) {}
}
