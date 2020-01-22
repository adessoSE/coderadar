package io.reflectoring.coderadar.vcs.port.driver.update;

import java.io.File;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateRepositoryCommand {
  private String remoteUrl;
  private File localDir;
  private String username;
  private String password;
}
