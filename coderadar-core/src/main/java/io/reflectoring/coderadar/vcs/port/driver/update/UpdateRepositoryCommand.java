package io.reflectoring.coderadar.vcs.port.driver.update;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

@Data
@NoArgsConstructor
public class UpdateRepositoryCommand {
  private String remoteUrl;
  private File localDir;
  private String username;
  private String password;
}
