package io.reflectoring.coderadar.projectadministration.port.driven.filepattern;

import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import java.util.List;

public interface ListFilePatternsOfProjectPort {
  List<FilePattern> listFilePatterns(Long projectId);
}
