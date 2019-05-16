package io.reflectoring.coderadar.core.projectadministration.port.driver.filepattern.get;

import io.reflectoring.coderadar.core.projectadministration.FilePatternNotFoundException;

public interface GetFilePatternUseCase {
  GetFilePatternResponse get(Long filePatternId) throws FilePatternNotFoundException;
}
