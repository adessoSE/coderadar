package io.reflectoring.coderadar.query.domain;

import lombok.Data;

@Data
public class ProfileRatingDTO {

  private Long complianceRating = 0L;

  private Long violationRating = 0L;
}
