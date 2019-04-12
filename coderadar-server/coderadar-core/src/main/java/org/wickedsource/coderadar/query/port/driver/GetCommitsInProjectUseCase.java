package org.wickedsource.coderadar.query.port.driver;

import org.wickedsource.coderadar.commit.domain.Commit;

import java.util.List;

public interface GetCommitsInProjectUseCase {
    List<Commit> get(Long projectId);
}
