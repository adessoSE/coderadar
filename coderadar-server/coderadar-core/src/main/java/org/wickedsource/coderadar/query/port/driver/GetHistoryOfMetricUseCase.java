package org.wickedsource.coderadar.query.port.driver;

import org.wickedsource.coderadar.core.rest.dates.series.Series;

public interface GetHistoryOfMetricUseCase {
    Series get(GetHistoryOfMetricCommand command);
}
