package io.reflectoring.coderadar.query.port.driver.criticalfiles;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetFilesWithContributorsCommand {
  @NotBlank
  @Size(min = 16)
  private String commitHash;

  @Min(1)
  private int numberOfContributors;
}
