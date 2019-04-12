package org.wickedsource.coderadar.analyzer.port.driver;

import org.wickedsource.coderadar.analyzingjob.domain.AnalyzingJob;

public interface GetAnalyzingStatusUseCase {
    AnalyzingJob get(Long projectId);
}
