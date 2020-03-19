package io.reflectoring.coderadar.query.port.driver;

import io.reflectoring.coderadar.contributor.port.driver.GetCriticalFilesCommand;
import io.reflectoring.coderadar.query.domain.ContributorsForFile;
import java.util.List;

public interface GetCriticalFilesUseCase {
  List<ContributorsForFile> getCriticalFiles(Long projectId, GetCriticalFilesCommand command);
}
