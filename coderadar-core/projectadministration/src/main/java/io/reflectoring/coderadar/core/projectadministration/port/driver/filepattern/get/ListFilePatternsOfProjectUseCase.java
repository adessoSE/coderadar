package io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.get;

import io.reflectoring.coderadar.core.projectadministration.ProjectNotFoundException;
import java.util.List;

public interface ListFilePatternsOfProjectUseCase {
  List<GetFilePatternResponse> listFilePatterns(Long projectId) throws ProjectNotFoundException;
}
