package io.reflectoring.coderadar.contributor.port.driver;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateContributorCommand {
  @NotBlank private String displayName;
}
