package io.reflectoring.coderadar.vcs.port.driver.update;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateRepositoryCommand {
  private String remoteUrl;
  private String localDir;
  private String username;
  private String password;
}
