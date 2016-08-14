package org.wickedsource.coderadar.qualityprofile.rest;

import org.springframework.hateoas.ResourceSupport;
import org.wickedsource.coderadar.qualityprofile.domain.MetricDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class QualityProfileResource extends ResourceSupport {

    @NotNull
    private String profileName;

    @NotNull
    @Size(min = 1)
    private List<MetricDTO> metrics = new ArrayList<>();

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
