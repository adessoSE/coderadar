package io.reflectoring.coderadar.projectadministration.port.driver.filepattern.get;

import java.util.List;

public interface ListFilePatternsOfProjectUseCase {
  List<GetFilePatternResponse> listFilePatterns(Long projectId);
}
