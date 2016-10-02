package org.wickedsource.coderadar.core.rest.dates;

public class Week {

    private final int year;

    private final int weekOfYear;

    public Week(int year, int weekOfYear) {
        this.year = year;
        this.weekOfYear = weekOfYear;
    }

    public int getYear() {
        return year;
    }

    public int getWeekOfYear() {
        return weekOfYear;
    }
}
