package io.reflectoring.coderadar.core.analyzer.port.driver;

import io.reflectoring.coderadar.core.analyzer.domain.AnalyzingJob;
import java.util.Optional;

public interface GetAnalyzingStatusUseCase {
  Optional<AnalyzingJob> get(Long projectId);
}
