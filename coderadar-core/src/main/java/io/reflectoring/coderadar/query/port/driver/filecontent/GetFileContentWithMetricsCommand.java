package io.reflectoring.coderadar.query.port.driver.filecontent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetFileContentWithMetricsCommand {
  @NotBlank private String commitHash;
  @NotBlank private String filepath;
}
