package io.reflectoring.coderadar.contributor.port.driver;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetContributorsForPathCommand {
  @NotBlank private String path;

  @NotBlank
  @Size(min = 16)
  private String commitHash;
}
