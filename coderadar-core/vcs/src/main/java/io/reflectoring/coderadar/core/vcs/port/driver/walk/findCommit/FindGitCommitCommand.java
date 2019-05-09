package io.reflectoring.coderadar.core.vcs.port.driver.walk.findCommit;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.eclipse.jgit.api.Git;

@Data
@AllArgsConstructor
public class FindGitCommitCommand {
  private Git gitClient;
  private String commitName;
}
