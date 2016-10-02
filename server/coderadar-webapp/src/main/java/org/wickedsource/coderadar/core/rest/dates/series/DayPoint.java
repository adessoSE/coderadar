package org.wickedsource.coderadar.core.rest.dates.series;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.wickedsource.coderadar.core.rest.dates.Day;
import org.wickedsource.coderadar.core.rest.dates.serialize.DayDeserializer;
import org.wickedsource.coderadar.core.rest.dates.serialize.DaySerializer;

import java.util.Calendar;
import java.util.Date;

@JsonTypeName("day")
public class DayPoint extends Point<Day, Long> {

    @JsonIgnore
    private Day day;

    @JsonIgnore
    private Long value;

    public DayPoint() {
    }

    public DayPoint(Day day, Long value) {
        this.day = day;
        this.value = value;
    }

    public static DayPoint from(Date date, Long value) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        Day day = new Day(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
        return new DayPoint(day, value);
    }

    @Override
    @JsonSerialize(using = DaySerializer.class)
    @JsonDeserialize(using = DayDeserializer.class)
    public Day getX() {
        return day;
    }

    @Override
    public Long getY() {
        return value;
    }

    public void setX(Day day) {
        this.day = day;
    }

    public void setY(Long value) {
        this.value = value;
    }
}
