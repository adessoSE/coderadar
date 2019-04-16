package io.reflectoring.coderadar.core.query.domain;

import lombok.Value;

@Value
public class Day implements Comparable<Day> {

    private final int year;

    private final int month;

    private final int dayOfMonth;

    @Override
    public int compareTo(Day o) {
        if (this.year < o.year) {
            return -1;
        }
        if (this.year > o.year) {
            return 1;
        }
        if (this.month < o.month) {
            return -1;
        }
        if (this.month > o.month) {
            return 1;
        }
        return Integer.compare(this.dayOfMonth, o.dayOfMonth);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Day day = (Day) o;

        return year == day.year && month == day.month && dayOfMonth == day.dayOfMonth;
    }

    @Override
    public int hashCode() {
        int result = year;
        result = 31 * result + month;
        result = 31 * result + dayOfMonth;
        return result;
    }

    @Override
    public String toString() {
        return "Day[" + "year=" + year + ", month=" + month + ", dayOfMonth=" + dayOfMonth + ']';
    }
}
