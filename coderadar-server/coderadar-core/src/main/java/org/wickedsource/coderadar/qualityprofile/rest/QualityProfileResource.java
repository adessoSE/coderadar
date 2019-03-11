package org.wickedsource.coderadar.qualityprofile.rest;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.ResourceSupport;
import org.wickedsource.coderadar.qualityprofile.domain.MetricDTO;

@EqualsAndHashCode(callSuper = true)
@Data
public class QualityProfileResource extends ResourceSupport {

  @NotNull private String profileName;

  @NotNull
  @Size(min = 1)
  private List<MetricDTO> metrics = new ArrayList<>();

  public void addMetric(MetricDTO metric) {
    this.metrics.add(metric);
  }
}
