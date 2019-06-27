package io.reflectoring.coderadar.analyzer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jmx.support.MetricType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileValuePerCommitDTO {

  @JsonIgnore private String profile;

  private MetricType metricType;

  private Long value;
}
