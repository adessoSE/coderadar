package io.reflectoring.coderadar.query.port.driver.filediff;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetFileDiffCommand {
  private String commitHash;
  private String filepath;
}
