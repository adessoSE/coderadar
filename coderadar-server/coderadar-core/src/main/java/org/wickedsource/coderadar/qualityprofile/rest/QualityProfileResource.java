package org.wickedsource.coderadar.qualityprofile.rest;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.wickedsource.coderadar.qualityprofile.domain.MetricDTO;

@EqualsAndHashCode
@Data
public class QualityProfileResource {

  private Long id;

  @NotNull private String profileName;

  @NotNull
  @Size(min = 1)
  private List<MetricDTO> metrics = new ArrayList<>();

  public void addMetric(MetricDTO metric) {
    this.metrics.add(metric);
  }
}
