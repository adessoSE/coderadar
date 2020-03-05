package io.reflectoring.coderadar.query.port.driver;

import io.reflectoring.coderadar.query.domain.ContributorsForFile;
import java.util.List;

public interface GetCriticalFilesUseCase {
  List<ContributorsForFile> getCriticalFiles(Long projectId, int numberOfContributors);
}
