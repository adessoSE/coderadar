package io.reflectoring.coderadar.contributor.port.driver;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetContributorsForPathCommand {
  @NotBlank private String path;
  @NotBlank private String commitHash;
}
