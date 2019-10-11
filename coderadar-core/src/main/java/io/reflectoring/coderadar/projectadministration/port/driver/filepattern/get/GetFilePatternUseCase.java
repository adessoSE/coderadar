package io.reflectoring.coderadar.projectadministration.port.driver.filepattern.get;

import io.reflectoring.coderadar.projectadministration.FilePatternNotFoundException;

public interface GetFilePatternUseCase {
  GetFilePatternResponse get(Long filePatternId) throws FilePatternNotFoundException;
}
