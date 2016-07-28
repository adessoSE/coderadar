package org.wickedsource.coderadar.metric.rest.metricvalue;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.ResourceSupport;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValuePerCommitDTO;
import org.wickedsource.coderadar.metric.domain.metricvalue.ProfileValuePerCommitDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class MetricOutputsResource extends ResourceSupport {

    private Map<String, OutputsPerCommitDTO> commits;

    public MetricOutputsResource() {
        this.commits = new HashMap();
    }

    public Map<String, OutputsPerCommitDTO> getCommits() {
        return this.commits;
    }

    public void setCommits(Map<String, OutputsPerCommitDTO> commits) {
        this.commits = commits;
    }

    @JsonIgnore
    public void setMetricValuesPerCommit(List<MetricValuePerCommitDTO> metricValuesPerCommit) {
        initCommits();
        for (MetricValuePerCommitDTO metricValue : metricValuesPerCommit) {
            String commit = metricValue.getCommit();
            OutputsPerCommitDTO outputForCommit = commits.get(commit);
            if (outputForCommit == null) {
                outputForCommit = new OutputsPerCommitDTO();
                commits.put(commit, outputForCommit);
            }
            outputForCommit.addMetricValue(metricValue.getMetric(), metricValue.getValue());
        }
    }

    private void initCommits() {
        if (commits == null) {
            commits = new HashMap<>();
        }
    }

    @JsonIgnore
    public void setProfileValuesPerCommit(List<ProfileValuePerCommitDTO> profileValuesPerCommit) {
        initCommits();
        for (ProfileValuePerCommitDTO profileValue : profileValuesPerCommit) {
            String commit = profileValue.getCommit();
            String profile = profileValue.getProfile();

            OutputsPerCommitDTO outputs = commits.get(commit);
            if (outputs == null) {
                outputs = new OutputsPerCommitDTO();
                commits.put(commit, outputs);
            }

            ProfileRatingDTO rating = outputs.getProfileRating(profile);
            if (rating == null) {
                rating = new ProfileRatingDTO();
                outputs.setProfileRating(profile, rating);
            }

            switch (profileValue.getMetricType()) {
                case COMPLIANCE:
                    rating.setComplianceRating(profileValue.getValue());
                    break;
                case VIOLATION:
                    rating.setViolationRating(profileValue.getValue());
                    break;
                default:
                    throw new IllegalStateException(String.format("unsupported MetricType: %s", profileValue.getMetricType()));
            }
        }
    }

    public void addAbsentProfiles(List<String> profiles) {
        for (String commit : commits.keySet()) {
            for (String profile : profiles) {
                commits.get(commit).getProfiles().putIfAbsent(profile, new ProfileRatingDTO());
            }
        }
    }

    public void addAbsentMetrics(List<String> metrics) {
        for (String commit : commits.keySet()) {
            for (String metric : metrics) {
                commits.get(commit).getMetrics().putIfAbsent(metric, 0L);
            }
        }
    }
}
