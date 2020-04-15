package io.reflectoring.coderadar.query.port.driver.filecontent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetFileContentWithMetricsCommand {
  private String commitHash;
  private String filepath;
}
