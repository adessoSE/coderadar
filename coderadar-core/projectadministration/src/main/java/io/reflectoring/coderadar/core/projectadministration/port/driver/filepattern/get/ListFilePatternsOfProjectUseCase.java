package io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.get;

import java.util.List;

public interface ListFilePatternsOfProjectUseCase {
  List<GetFilePatternResponse> listFilePatterns(Long projectId);
}
