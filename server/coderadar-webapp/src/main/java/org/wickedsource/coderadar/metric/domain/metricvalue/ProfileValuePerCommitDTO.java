package org.wickedsource.coderadar.metric.domain.metricvalue;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.wickedsource.coderadar.metric.domain.qualityprofile.MetricType;

public class ProfileValuePerCommitDTO {

    @JsonIgnore
    private String commit;

    @JsonIgnore
    private String profile;

    private MetricType metricType;

    private Long value;

    public ProfileValuePerCommitDTO() {

    }

    public ProfileValuePerCommitDTO(String commitName, String profile, MetricType metricType, Long value) {
        this.commit = commitName;
        this.profile = profile;
        this.metricType = metricType;
        this.value = value;
    }

    public String getProfile() {
        return profile;
    }

    public Long getValue() {
        return value;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public MetricType getMetricType() {
        return metricType;
    }

    public void setMetricType(MetricType metricType) {
        this.metricType = metricType;
    }
}
