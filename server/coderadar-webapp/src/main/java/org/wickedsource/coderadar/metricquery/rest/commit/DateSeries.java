package org.wickedsource.coderadar.metricquery.rest.commit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.*;
import org.wickedsource.coderadar.core.rest.dates.series.Point;
import org.wickedsource.coderadar.core.rest.dates.series.Series;

/**
 * A series of points in time (X coordinate) that each have a numeric value (Y coordinate).
 *
 * @param <T> the date type (X coordinate of the points)
 */
public class DateSeries<T extends Comparable> extends Series<T, Long> {

  @JsonIgnore private Map<T, Point<T, Long>> pointsByDate = new HashMap<>();

  @Override
  public void addPoint(Point<T, Long> point) {
    Point<T, Long> previousPoint = pointsByDate.put(point.getX(), point);
    if (previousPoint != null) {
      super.removePoint(previousPoint);
    }
    super.addPoint(point);
  }

  /**
   * Goes through all points currently in this series. If the numeric value (Y coordinate) of a
   * point is NULL, its value is set to the value of the chronologically previous point.
   */
  public void fillMissingValuesWithPreviousValue() {
    List<T> sortedDates = new ArrayList<>(pointsByDate.keySet());
    Collections.sort(sortedDates);
    Long previousValue = null;
    for (T day : sortedDates) {
      Point<T, Long> point = pointsByDate.get(day);
      if (point.getY() == null) {
        point.setY(previousValue);
      }
      previousValue = point.getY();
    }
  }

  @Override
  public void removePoint(Point<T, Long> point) {
    pointsByDate.remove(point.getX());
    super.removePoint(point);
  }
}
