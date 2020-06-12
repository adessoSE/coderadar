package io.reflectoring.coderadar.contributor.port.driver;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateContributorCommand {
  @NotBlank private String displayName;
}
