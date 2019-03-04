package org.wickedsource.coderadar.metricquery.rest.commit.profilerating;

import lombok.Data;

@Data
public class ProfileRatingDTO {

  private Long complianceRating = 0L;

  private Long violationRating = 0L;
}
