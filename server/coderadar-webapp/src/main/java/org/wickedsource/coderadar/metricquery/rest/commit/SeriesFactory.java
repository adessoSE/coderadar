package org.wickedsource.coderadar.metricquery.rest.commit;

import org.springframework.stereotype.Component;
import org.wickedsource.coderadar.core.rest.dates.Day;
import org.wickedsource.coderadar.core.rest.dates.series.DayPoint;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
public class SeriesFactory {

    /**
     * Creates a series with one point for each day within a date range. All points initially have a NULL value.
     *
     * @param startDate the date at which to start the series (inclusive)
     * @param endDate   the date at which to end the series (inclusive)
     * @return a Series containing a NULL value for day in the given interval between startDate and endDate.
     */
    public DateSeries daySeries(LocalDate startDate, LocalDate endDate) {
        DateSeries series = new DateSeries();
        for (int i = 0; i < DAYS.between(startDate, endDate); i++) {
            LocalDate nextDate = startDate.plus(i, ChronoUnit.DAYS);
            series.addPoint(new DayPoint(new Day(nextDate.getYear(), nextDate.getMonthValue(), nextDate.getDayOfMonth()), null));
        }
        return series;
    }
}
