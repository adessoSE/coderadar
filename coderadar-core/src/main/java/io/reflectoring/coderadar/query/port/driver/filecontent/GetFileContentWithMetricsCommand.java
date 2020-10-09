package io.reflectoring.coderadar.query.port.driver.filecontent;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetFileContentWithMetricsCommand {
  @NotBlank
  @Size(min = 16)
  private String commitHash;

  @NotBlank private String filepath;
}
