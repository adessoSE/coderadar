package io.reflectoring.coderadar.vcs.port.driven;

import java.io.File;

public interface CloneRepositoryPort {
  void cloneRepository(String remoteUrl, File localDir);
}
