package io.reflectoring.coderadar.core.vcs.port.driven;

import java.io.File;
import org.eclipse.jgit.api.Git;

public interface CloneRepositoryPort {
  Git cloneRepository(String remoteUrl, File localDir);
}
