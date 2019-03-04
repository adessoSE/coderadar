package org.wickedsource.coderadar.metric.domain.metricvalue;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.wickedsource.coderadar.qualityprofile.domain.MetricType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileValuePerCommitDTO {

  @JsonIgnore private String profile;

  private MetricType metricType;

  private Long value;
}
