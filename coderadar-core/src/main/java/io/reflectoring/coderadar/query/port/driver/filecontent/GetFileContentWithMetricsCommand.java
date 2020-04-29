package io.reflectoring.coderadar.query.port.driver.filecontent;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetFileContentWithMetricsCommand {
  @NotBlank private String commitHash;
  @NotBlank private String filepath;
}
