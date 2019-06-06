package io.reflectoring.coderadar.vcs.port.driven;

import java.io.File;
import org.eclipse.jgit.api.Git;

public interface CloneRepositoryPort {
  Git cloneRepository(String remoteUrl, File localDir);
}
