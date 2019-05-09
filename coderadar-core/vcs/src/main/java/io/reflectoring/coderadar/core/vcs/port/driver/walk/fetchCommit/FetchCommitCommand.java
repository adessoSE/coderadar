package io.reflectoring.coderadar.core.vcs.port.driver.walk.fetchCommit;

import java.net.URL;
import java.nio.file.Path;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FetchCommitCommand {
  String commitName;
  URL url;
  Path localDir;
}
