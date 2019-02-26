package org.wickedsource.coderadar.qualityprofile.rest;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.wickedsource.coderadar.qualityprofile.domain.MetricDTO;

public class QualityProfileResource {

  private Long id;

  @NotNull private String profileName;

  @NotNull
  @Size(min = 1)
  private List<MetricDTO> metrics = new ArrayList<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getProfileName() {
    return profileName;
  }

  public void setProfileName(String profileName) {
    this.profileName = profileName;
  }

  public List<MetricDTO> getMetrics() {
    return metrics;
  }

  public void setMetrics(List<MetricDTO> metrics) {
    this.metrics = metrics;
  }

  public void addMetric(MetricDTO metric) {
    this.metrics.add(metric);
  }
}
