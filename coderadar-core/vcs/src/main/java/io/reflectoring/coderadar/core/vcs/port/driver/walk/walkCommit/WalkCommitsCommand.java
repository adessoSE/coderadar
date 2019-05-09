package io.reflectoring.coderadar.core.vcs.port.driver.walk.walkCommit;

import io.reflectoring.coderadar.core.vcs.domain.CommitProcessor;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.eclipse.jgit.api.Git;

@Data
@AllArgsConstructor
public class WalkCommitsCommand {
    private Git gitClient;
    private CommitProcessor commitProcessor;
}
