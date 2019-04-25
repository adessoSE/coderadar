package io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;
import java.util.List;

public interface GetFilePatternFromProjectUseCase {
  List<FilePattern> getFilePatterns(Long projectId);
}
