package io.reflectoring.coderadar.core.analyzer.port.driven;

import io.reflectoring.coderadar.core.analyzer.domain.AnalyzingJob;
import java.util.Optional;

public interface GetAnalyzingStatusPort {
  Optional<AnalyzingJob> get(Long projectId);
}
