package io.reflectoring.coderadar.core.vcs.port.driver.walk.fetchCommit;

import java.net.URL;
import java.nio.file.Path;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FetchCommitCommand {
  private String commitName;
  private URL url;
  // private Path localDir;  //TODO: Tests don't work if this exists. https://github.com/neo4j/neo4j-ogm/issues/179, no idea why
}
