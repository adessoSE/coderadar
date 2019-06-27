package io.reflectoring.coderadar.projectadministration.port.driver.filepattern.get;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import java.util.List;

public interface ListFilePatternsOfProjectUseCase {
  List<GetFilePatternResponse> listFilePatterns(Long projectId) throws ProjectNotFoundException;
}
