package org.wickedsource.coderadar.metricquery.rest.commit.profilerating;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.ResourceSupport;
import org.wickedsource.coderadar.metric.domain.metricvalue.ProfileValuePerCommitDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class CommitProfileRatingsOutputResource extends ResourceSupport {

    private Map<String, ProfileRatingDTO> profiles;

    public CommitProfileRatingsOutputResource() {
    }

    @JsonIgnore
    public void addProfileValues(List<ProfileValuePerCommitDTO> profileValuesPerCommit) {
        initProfiles();
        for (ProfileValuePerCommitDTO profileValue : profileValuesPerCommit) {
            String profile = profileValue.getProfile();

            ProfileRatingDTO rating = getProfileRating(profile);
            if (rating == null) {
                rating = new ProfileRatingDTO();
                setProfileRating(profile, rating);
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
        initProfiles();
        for (String profile : profiles) {
            this.profiles.putIfAbsent(profile, new ProfileRatingDTO());
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
}
