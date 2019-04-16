package io.reflectoring.coderadar.core.query.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = DayPoint.class, name = "day"),
        @JsonSubTypes.Type(value = WeekPoint.class, name = "week"),
        @JsonSubTypes.Type(value = MonthPoint.class, name = "month"),
        @JsonSubTypes.Type(value = YearPoint.class, name = "year")
})
public abstract class Point<X, Y> {

    public abstract X getX();

    public abstract Y getY();

    public abstract void setX(X x);

    public abstract void setY(Y y);

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Point<?, ?> point = (Point<?, ?>) o;

        if (getX() != null ? !getX().equals(point.getX()) : point.getX() != null) {
            return false;
        }
        return getY() != null ? getY().equals(point.getY()) : point.getY() == null;
    }

    @Override
    public int hashCode() {
        int result = getX() != null ? getX().hashCode() : 0;
        result = 31 * result + (getY() != null ? getY().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("[%s x=%s; y=%s]", getClass().getSimpleName(), getX(), getY());
    }
}
