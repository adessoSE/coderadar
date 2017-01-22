package org.wickedsource.coderadar.core.rest.dates.series;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.wickedsource.coderadar.core.rest.dates.Month;

@JsonTypeName("month")
public class MonthPoint extends Point<Month, Long> {

  @JsonIgnore private Month month;

  @JsonIgnore private Long value;

  public MonthPoint(Month month, Long value) {
    this.month = month;
    this.value = value;
  }

  @Override
  public Month getX() {
    return month;
  }

  @Override
  public Long getY() {
    return value;
  }

  @Override
  public void setX(Month month) {
    this.month = month;
  }

  @Override
  public void setY(Long value) {
    this.value = value;
  }
}
