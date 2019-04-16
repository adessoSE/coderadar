package io.reflectoring.coderadar.core.query.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;

@JsonTypeName("year")
@AllArgsConstructor
public class YearPoint extends Point<Year, Long> {

  @JsonIgnore private Year year;

  @JsonIgnore private Long value;

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
