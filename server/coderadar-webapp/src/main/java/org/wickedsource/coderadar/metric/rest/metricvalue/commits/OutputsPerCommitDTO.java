package org.wickedsource.coderadar.metric.rest.metricvalue.commits;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.wickedsource.coderadar.metric.rest.metricvalue.ProfileRatingDTO;

import java.util.HashMap;
import java.util.Map;

public class OutputsPerCommitDTO {

    private Map<String, Long> metrics;

    private Map<String, ProfileRatingDTO> profiles;

    public void addMetricValue(String metric, Long value) {
        initMetrics();
        metrics.put(metric, value);
    }

    private void initMetrics() {
        if (metrics == null) {
            metrics = new HashMap<>();
        }
    }

    private void initProfiles() {
        if (profiles == null) {
            profiles = new HashMap<>();
        }
    }

    @JsonIgnore
    public ProfileRatingDTO getProfileRating(String profile) {
        if (profiles == null) {
            return null;
        }
        return profiles.get(profile);
    }

    @JsonIgnore
    public void setProfileRating(String profile, ProfileRatingDTO rating) {
        initProfiles();
        profiles.put(profile, rating);
    }

    public void addProfileRating(ProfileRatingDTO rating, String profile) {
        initProfiles();
        profiles.put(profile, rating);
    }

    public Map<String, ProfileRatingDTO> getProfiles() {
        return profiles;
    }

    public void setProfiles(Map<String, ProfileRatingDTO> profiles) {
        this.profiles = profiles;
    }

    public Map<String, Long> getMetrics() {
        return metrics;
    }

    public void setMetrics(Map<String, Long> metrics) {
        this.metrics = metrics;
    }
}
