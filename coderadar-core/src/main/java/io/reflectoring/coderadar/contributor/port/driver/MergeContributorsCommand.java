package io.reflectoring.coderadar.contributor.port.driver;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MergeContributorsCommand {
  private Long firstContributorId; // validation annotation?
  private Long secondContributorId; // validation annotation?
  @NotBlank private String displayName;
}
