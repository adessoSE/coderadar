package org.wickedsource.coderadar.metric.rest.metricvalue;

public class ProfileRatingDTO {

    private Long complianceRating = 0L;

    private Long violationRating = 0L;

    public Long getComplianceRating() {
        return complianceRating;
    }

    public void setComplianceRating(Long complianceRating) {
        this.complianceRating = complianceRating;
    }

    public Long getViolationRating() {
        return violationRating;
    }

    public void setViolationRating(Long violationRating) {
        this.violationRating = violationRating;
    }
}
