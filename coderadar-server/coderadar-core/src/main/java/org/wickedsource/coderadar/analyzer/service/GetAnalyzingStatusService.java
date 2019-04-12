package org.wickedsource.coderadar.analyzer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.analyzer.port.driven.GetAnalyzingStatusPort;
import org.wickedsource.coderadar.analyzer.port.driver.GetAnalyzingStatusUseCase;
import org.wickedsource.coderadar.analyzingjob.domain.AnalyzingJob;

@Service
public class GetAnalyzingStatusService implements GetAnalyzingStatusUseCase {
    private final GetAnalyzingStatusPort getAnalyzingStatusPort;

    @Autowired
    public GetAnalyzingStatusService(GetAnalyzingStatusPort getAnalyzingStatusPort) {
        this.getAnalyzingStatusPort = getAnalyzingStatusPort;
    }

    @Override
    public AnalyzingJob get(Long projectId) {
        return getAnalyzingStatusPort.get(projectId);
    }
}
