package org.wickedsource.coderadar.query.port.driven;

import org.wickedsource.coderadar.core.rest.dates.series.Series;
import org.wickedsource.coderadar.query.port.driver.GetHistoryOfMetricCommand;

public interface GetHistoryOfMetricPort {
    Series get(GetHistoryOfMetricCommand command);
}
