package io.reflectoring.coderadar.vcs.port.driver.walk.fetchCommit;

import java.net.URL;
import java.nio.file.Path;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FetchCommitCommand {
  private String commitName;
  private URL url;
  private Path localDir;
}
