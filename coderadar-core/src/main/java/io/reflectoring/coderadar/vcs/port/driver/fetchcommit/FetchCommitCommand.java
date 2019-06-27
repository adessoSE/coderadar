package io.reflectoring.coderadar.vcs.port.driver.fetchcommit;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.net.URL;
import java.nio.file.Path;

@Data
@AllArgsConstructor
public class FetchCommitCommand {
  private String commitName;
  private URL url;
  private Path localDir;
}
