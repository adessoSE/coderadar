package io.reflectoring.coderadar.projectadministration.port.driven.filepattern;

import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import java.util.Optional;

public interface GetFilePatternPort {
  Optional<FilePattern> get(Long id);
}
