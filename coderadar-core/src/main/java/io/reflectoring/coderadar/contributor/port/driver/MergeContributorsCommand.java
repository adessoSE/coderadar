package io.reflectoring.coderadar.contributor.port.driver;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MergeContributorsCommand {
  private long firstContributorId;
  private long secondContributorId;
  @NotBlank private String displayName;
}
