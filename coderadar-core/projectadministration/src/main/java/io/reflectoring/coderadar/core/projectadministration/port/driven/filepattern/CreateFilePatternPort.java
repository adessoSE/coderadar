package io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;

public interface CreateFilePatternPort {
  FilePattern createFilePattern(FilePattern filePattern);
}
