package io.reflectoring.coderadar.vcs.adapter;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.vcs.RevCommitMapper;
import io.reflectoring.coderadar.vcs.port.driven.FindCommitPort;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FindCommitAdapter implements FindCommitPort {

  private final CoderadarConfigurationProperties coderadarConfigurationProperties;

  @Autowired
  public FindCommitAdapter(CoderadarConfigurationProperties coderadarConfigurationProperties) {
    this.coderadarConfigurationProperties = coderadarConfigurationProperties;
  }

  @Override
  public Commit findCommit(Path repositoryRoot, String name) {
    Git git;
    try {
      Repository repository;
      repositoryRoot =
          Paths.get(coderadarConfigurationProperties.getWorkdir() + "/projects/" + repositoryRoot);
      FileRepositoryBuilder builder = new FileRepositoryBuilder();
      repository =
          builder
              .setWorkTree(repositoryRoot.toFile())
              .setGitDir(new File(repositoryRoot + "/.git"))
              .build();
      git = new Git(repository);

      ObjectId commitId = git.getRepository().resolve(name);
      Iterable<RevCommit> commits = git.log().add(commitId).call();
      return RevCommitMapper.map(commits.iterator().next());

    } catch (MissingObjectException e) {
      return null;
    } catch (Exception e) {
      throw new IllegalStateException(
          String.format("Error accessing git repository at %s", repositoryRoot), e);
    }
  }
}
