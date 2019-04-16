package io.reflectoring.coderadar.core.query.port.driven;

import java.util.List;

public interface GetAvailableMetricsInProjectPort {
    List<String> get(Long projectId);
}
