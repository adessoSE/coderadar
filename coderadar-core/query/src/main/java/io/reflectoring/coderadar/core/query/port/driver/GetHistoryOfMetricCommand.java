package io.reflectoring.coderadar.core.query.port.driver;

import io.reflectoring.coderadar.core.query.domain.Interval;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetHistoryOfMetricCommand {
  @NotBlank private Long projectId;
  @NotBlank private String metricName;
  private Date start;
  private Date end;
  private Interval interval;
}
