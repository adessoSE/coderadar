package org.wickedsource.coderadar.analyzer.port.driven;

import org.wickedsource.coderadar.analyzingjob.domain.AnalyzingJob;

public interface GetAnalyzingStatusPort {
    AnalyzingJob get(Long projectId);
}
