package io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;

import java.util.Optional;

public interface GetFilePatternPort {
  Optional<FilePattern> get(Long id);
}
