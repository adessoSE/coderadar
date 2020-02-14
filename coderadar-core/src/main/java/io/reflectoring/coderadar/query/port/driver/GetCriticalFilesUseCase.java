package io.reflectoring.coderadar.query.port.driver;

import java.util.List;

public interface GetCriticalFilesUseCase {
  List<String> getCriticalFiles(Long projectId);
}
