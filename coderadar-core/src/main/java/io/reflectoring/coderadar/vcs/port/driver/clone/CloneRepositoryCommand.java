package io.reflectoring.coderadar.vcs.port.driver.clone;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;

@Data
@AllArgsConstructor
public class CloneRepositoryCommand {
  private String remoteUrl;
  private File localDir;
  private String username;
  private String password;
}
