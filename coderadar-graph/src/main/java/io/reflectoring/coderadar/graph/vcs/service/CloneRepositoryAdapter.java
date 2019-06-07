package io.reflectoring.coderadar.graph.vcs.service;

import io.reflectoring.coderadar.vcs.port.driven.CloneRepositoryPort;
import java.io.File;
import org.eclipse.jgit.api.Git;
import org.springframework.stereotype.Service;

@Service
public class CloneRepositoryAdapter implements CloneRepositoryPort {

  @Override
  public Git cloneRepository(String remoteUrl, File localDir) {
    return null;
  }
}
