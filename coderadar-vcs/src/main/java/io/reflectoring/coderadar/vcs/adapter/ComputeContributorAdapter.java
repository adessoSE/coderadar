package io.reflectoring.coderadar.vcs.adapter;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.contributor.domain.Contributor;
import io.reflectoring.coderadar.contributor.port.driven.ComputeContributorsPort;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.stereotype.Service;

@Service
public class ComputeContributorAdapter implements ComputeContributorsPort {
  private final CoderadarConfigurationProperties coderadarConfigurationProperties;

  public ComputeContributorAdapter(
      CoderadarConfigurationProperties coderadarConfigurationProperties) {
    this.coderadarConfigurationProperties = coderadarConfigurationProperties;
  }

  @Override
  public List<Contributor> computeContributors(Long projectId, String repositoryRoot) {
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

      Map<String, Set<String>> contributors = new HashMap<>();
      for (RevCommit rc : revCommits) {
        String email = rc.getAuthorIdent().getEmailAddress();
        String name = rc.getAuthorIdent().getName();
        if (contributors.containsKey(email)) {
          contributors.get(email).add(name);
        } else {
          contributors.put(email, new HashSet<>(Collections.singletonList(name)));
        }
      }

      List<Contributor> contributorsToSave = new ArrayList<>(contributors.size());
      for (Map.Entry<String, Set<String>> entry : contributors.entrySet()) {
        Contributor contributor =
            new Contributor().setEmail(entry.getKey()).setNames(entry.getValue());
        contributorsToSave.add(contributor);
      }
      return contributorsToSave;
    } catch (Exception e) {
      throw new IllegalStateException(
          String.format("Error accessing git repository at %s", repositoryRoot), e);
    }
  }
}
