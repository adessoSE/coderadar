package io.reflectoring.coderadar.core.vcs.port.driver.walk.fetchCommit;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.net.URL;
import java.nio.file.Path;

@Data
@AllArgsConstructor
public class FetchCommitCommand {
    String commitName;
    URL url;
    Path localDir;
}
