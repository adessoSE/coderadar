package io.reflectoring.coderadar.contributor.port.driver;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCriticalFilesCommand {
  @NotBlank private String commitHash;

  @Min(1)
  private int numberOfContributors;
}
