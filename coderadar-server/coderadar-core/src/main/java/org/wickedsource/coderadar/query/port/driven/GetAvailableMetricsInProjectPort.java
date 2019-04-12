package org.wickedsource.coderadar.query.port.driven;

import java.util.List;

public interface GetAvailableMetricsInProjectPort {
    List<String> get(Long projectId);
}
