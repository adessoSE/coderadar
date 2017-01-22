package org.wickedsource.coderadar.core.rest.dates.series;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.wickedsource.coderadar.core.rest.dates.Year;

@JsonTypeName("year")
public class YearPoint extends Point<Year, Long> {

  @JsonIgnore private Year year;

  @JsonIgnore private Long value;

  public YearPoint(Year year, Long value) {
    this.year = year;
    this.value = value;
  }

  @Override
  public Year getX() {
    return year;
  }

  @Override
  public Long getY() {
    return value;
  }

  @Override
  public void setX(Year year) {
    this.year = year;
  }

  @Override
  public void setY(Long value) {
    this.value = value;
  }
}
