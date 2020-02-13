package io.reflectoring.coderadar.vcs;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.stereotype.Component;

@Component
public class RevCommitHelper {
  private final CoderadarConfigurationProperties coderadarConfigurationProperties;

  public RevCommitHelper(CoderadarConfigurationProperties coderadarConfigurationProperties) {
    this.coderadarConfigurationProperties = coderadarConfigurationProperties;
  }

  public List<RevCommit> getRevCommits(String repositoryRoot) {
    Git git;
    try {
      Path actualPath =
          Paths.get(coderadarConfigurationProperties.getWorkdir() + "/projects/" + repositoryRoot);

      FileRepositoryBuilder builder = new FileRepositoryBuilder();
      Repository repository =
          builder
              .setWorkTree(actualPath.toFile())
              .setGitDir(new java.io.File(actualPath + "/.git"))
              .build();
      git = new Git(repository);

      List<RevCommit> revCommits = new ArrayList<>();
      git.log().call().forEach(revCommits::add);
      return revCommits;
    } catch (Exception e) {
      throw new IllegalStateException(
          String.format("Error accessing git repository at %s", repositoryRoot), e);
    }
  }
}
