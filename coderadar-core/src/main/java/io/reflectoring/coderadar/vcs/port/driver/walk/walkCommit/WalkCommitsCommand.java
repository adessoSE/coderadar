package io.reflectoring.coderadar.vcs.port.driver.walk.walkCommit;

import io.reflectoring.coderadar.vcs.port.driver.ProcessCommitUseCase;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.eclipse.jgit.api.Git;

@Data
@AllArgsConstructor
public class WalkCommitsCommand {
  private Git gitClient;
  private ProcessCommitUseCase commitProcessor;
}
