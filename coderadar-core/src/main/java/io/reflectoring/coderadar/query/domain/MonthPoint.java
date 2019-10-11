package io.reflectoring.coderadar.query.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;

@JsonTypeName("month")
@AllArgsConstructor
public class MonthPoint extends Point<Month, Long> {

  @JsonIgnore private Month month;

  @JsonIgnore private Long value;

  @Override
  public Month getX() {
    return month;
  }

  @Override
  public void setX(Month month) {
    this.month = month;
  }

  @Override
  public Long getY() {
    return value;
  }

  @Override
  public void setY(Long value) {
    this.value = value;
  }
}
