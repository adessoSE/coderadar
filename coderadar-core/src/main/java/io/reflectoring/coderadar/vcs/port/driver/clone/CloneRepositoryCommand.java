package io.reflectoring.coderadar.vcs.port.driver.clone;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CloneRepositoryCommand {
  private String remoteUrl;
  private String localDir;
  private String username;
  private String password;
}
