package org.wickedsource.coderadar.metricquery.rest.commit.metric;

import java.util.ArrayList;
import java.util.List;
import org.wickedsource.coderadar.core.rest.dates.series.Point;

public class MetricValueHistoryResource<T> {

  private List<Point<T, Long>> points = new ArrayList<>();

  public List<Point<T, Long>> getPoints() {
    return points;
  }

  public void setPoints(List<Point<T, Long>> points) {
    this.points = points;
  }
}
