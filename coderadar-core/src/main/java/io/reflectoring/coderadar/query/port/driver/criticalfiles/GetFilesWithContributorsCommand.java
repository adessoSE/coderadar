package io.reflectoring.coderadar.query.port.driver.criticalfiles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetFilesWithContributorsCommand {
  @NotBlank private String commitHash;

  @Min(1)
  private int numberOfContributors;
}
