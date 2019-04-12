package org.wickedsource.coderadar.query.port.driven;

import org.wickedsource.coderadar.commit.domain.Commit;

import java.util.List;

public interface GetCommitsInProjectPort {
    List<Commit> get(Long projectId);
}
