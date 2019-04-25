package io.reflectoring.coderadar.core.query.domain;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class Series<X, Y> {

  @JsonUnwrapped private List<Point<X, Y>> points = new ArrayList<>();

  public Series(List<Point<X, Y>> points) {
    this.points = points;
  }

  public void setPoints(List<Point<X, Y>> points) {
    this.points = points;
  }

  public List<Point<X, Y>> getPoints() {
    return points;
  }

  public void addPoint(Point<X, Y> point) {
    this.points.add(point);
  }

  public void removePoint(Point<X, Y> point) {
    this.points.remove(point);
  }
}
