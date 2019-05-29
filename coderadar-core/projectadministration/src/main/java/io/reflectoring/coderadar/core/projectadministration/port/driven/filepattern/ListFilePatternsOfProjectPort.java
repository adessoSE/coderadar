package io.reflectoring.coderadar.core.projectadministration.port.driven.filepattern;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;

import java.util.List;

public interface ListFilePatternsOfProjectPort {
  List<FilePattern> listFilePatterns(Long projectId);
}
