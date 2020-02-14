package io.reflectoring.coderadar.query.port.driven;

import java.util.List;

public interface GetCriticalFilesPort {
  List<String> getCriticalFiles(Long projectId);
}
