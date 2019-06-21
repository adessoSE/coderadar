package io.reflectoring.coderadar.analyzer.port.driven;

import io.reflectoring.coderadar.analyzer.domain.File;

public interface SaveFilePort {
  void save(File file);
}
