package org.wickedsource.coderadar.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.query.port.driven.GetCommitsInProjectPort;
import org.wickedsource.coderadar.query.port.driver.GetCommitsInProjectUseCase;

import java.util.List;

@Service
public class GetCommitsInProjectService implements GetCommitsInProjectUseCase {
    private final GetCommitsInProjectPort getCommitsInProjectPort;

    @Autowired
    public GetCommitsInProjectService(GetCommitsInProjectPort getCommitsInProjectPort) {
        this.getCommitsInProjectPort = getCommitsInProjectPort;
    }

    @Override
    public List<Commit> get(Long projectId) {
        return getCommitsInProjectPort.get(projectId);
    }
}
