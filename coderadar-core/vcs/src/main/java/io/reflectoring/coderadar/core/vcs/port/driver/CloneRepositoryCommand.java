package io.reflectoring.coderadar.core.vcs.port.driver;

import java.io.File;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CloneRepositoryCommand {
  private String remoteUrl;
  private File localDir;
}