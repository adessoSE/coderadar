package io.reflectoring.coderadar.core.query.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@JsonTypeName("day")
@AllArgsConstructor
@NoArgsConstructor
public class DayPoint extends Point<Day, Long> {

    @JsonIgnore
    private Day day;

    @JsonIgnore
    private Long value;

    public static DayPoint from(Date date, Long value) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        Day day =
                new Day(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
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
