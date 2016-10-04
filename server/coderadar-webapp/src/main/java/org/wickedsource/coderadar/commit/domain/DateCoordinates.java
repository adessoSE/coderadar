package org.wickedsource.coderadar.commit.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Embeddable
public class DateCoordinates {

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Integer month;

    @Column(nullable = false)
    private Integer dayOfMonth;

    @Column(nullable = false)
    private Integer weekOfYear;

    @Column(nullable = false)
    private Integer yearOfWeek;

    public DateCoordinates() {

    }

    public DateCoordinates(Date date, Locale locale) {
        updateFromDate(date, locale);
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public Integer getWeekOfYear() {
        return weekOfYear;
    }

    public void setWeekOfYear(Integer weekOfYear) {
        this.weekOfYear = weekOfYear;
    }

    /**
     * The year to which the week belongs. This is only significant for a week that laps over the end of a year into
     * the next one. Such weeks are either counted to the previous or the next year.
     */
    public Integer getYearOfWeek() {
        return yearOfWeek;
    }

    public void setYearOfWeek(Integer yearOfWeek) {
        this.yearOfWeek = yearOfWeek;
    }


    public void updateFromDate(Date date, Locale locale) {
        Calendar c = Calendar.getInstance(locale);
        c.setTime(date);
        this.dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        this.year = c.get(Calendar.YEAR);
        this.month = c.get(Calendar.MONTH) + 1;
        this.weekOfYear = c.get(Calendar.WEEK_OF_YEAR);

        if (weekOfYear == 1 && c.get(Calendar.DAY_OF_YEAR) > 7) {
            // it's the first week of the current year!
            this.yearOfWeek = this.year;
        } else if (weekOfYear >= 52 && c.get(Calendar.DAY_OF_YEAR) < 358) {
            // it's the last week of the previous year!
            this.yearOfWeek = this.year - 1;
        } else {
            this.yearOfWeek = this.year;
        }
    }
}
