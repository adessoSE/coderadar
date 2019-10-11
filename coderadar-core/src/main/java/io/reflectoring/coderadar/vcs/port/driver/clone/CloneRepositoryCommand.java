package io.reflectoring.coderadar.vcs.port.driver.clone;

import java.io.File;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CloneRepositoryCommand {
  private String remoteUrl;
  private File localDir;
}
