package org.wickedsource.coderadar.core.rest.dates.series;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.wickedsource.coderadar.core.rest.dates.Week;

@JsonTypeName("week")
public class WeekPoint extends Point<Week, Long> {

    @JsonIgnore
    private Week week;

    @JsonIgnore
    private Long value;

    public WeekPoint() {
    }

    public WeekPoint(Week week, Long value) {
        this.week = week;
        this.value = value;
    }

    @Override
    public Week getX() {
        return week;
    }

    @Override
    public Long getY() {
        return value;
    }

    @Override
    public void setX(Week week) {
        this.week = week;
    }

    @Override
    public void setY(Long value) {
        this.value = value;
    }
}
