package io.reflectoring.coderadar.core.query.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@JsonTypeName("week")
@AllArgsConstructor
@NoArgsConstructor
public class WeekPoint extends Point<Week, Long> {

  @JsonIgnore private Week week;

  @JsonIgnore private Long value;

  @Override
  @JsonSerialize(using = WeekSerializer.class)
  @JsonDeserialize(using = WeekDeserializer.class)
  public Week getX() {
    return week;
  }

  @Override
  public Long getY() {
    return value;
  }

  @Override
  public void setX(Week week) {
    this.week = week;
  }

  @Override
  public void setY(Long value) {
    this.value = value;
  }
}
