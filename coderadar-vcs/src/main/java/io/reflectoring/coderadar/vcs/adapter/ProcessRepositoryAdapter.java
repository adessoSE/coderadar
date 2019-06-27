package io.reflectoring.coderadar.vcs.adapter;

import io.reflectoring.coderadar.vcs.Counter;
import io.reflectoring.coderadar.vcs.RevCommitMapper;
import io.reflectoring.coderadar.vcs.UnableToProcessRepositoryException;
import io.reflectoring.coderadar.vcs.domain.CommitFilter;
import io.reflectoring.coderadar.vcs.domain.CommitProcessor;
import io.reflectoring.coderadar.vcs.domain.VcsCommit;
import io.reflectoring.coderadar.vcs.port.driven.ProcessRepositoryPort;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

@Service
public class ProcessRepositoryAdapter implements ProcessRepositoryPort {

  private final CheckRepositoryAdapter checkRepositoryAdapter;

  @Autowired
  public ProcessRepositoryAdapter(CheckRepositoryAdapter checkRepositoryAdapter) {
    this.checkRepositoryAdapter = checkRepositoryAdapter;
  }

  @Override
  public void processRepository(
      Path repositoryRoot, CommitProcessor processor, CommitFilter... filter)
      throws UnableToProcessRepositoryException {
    Collection<CommitFilter> filters = Arrays.asList(filter);
    repositoryRoot = Paths.get("coderadar-workdir/projects/" + repositoryRoot);
    if (checkRepositoryAdapter.isRepository(repositoryRoot)) {
      Repository repository;
      try {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        repository =
            builder
                .setWorkTree(repositoryRoot.toFile())
                .setGitDir(new File(repositoryRoot + "/.git"))
                .build();
        Git git = new Git(repository);
        final Counter currentSequenceNumber = new Counter(getCommitCount(git));
        git.log()
            .call()
            .forEach(
                revCommit -> {
                  VcsCommit vcsCommit = RevCommitMapper.map(revCommit);
                  if (shouldBeProcessed(vcsCommit, filters)) {
                    try {
                      processor.processCommit(vcsCommit);
                    } catch (IOException e) {
                      e.printStackTrace();
                    }
                  }
                  currentSequenceNumber.decrement();
                });
      } catch (IOException | GitAPIException e) {
        throw new UnableToProcessRepositoryException(e.getMessage());
      }
    } else {
      throw new UnableToProcessRepositoryException(repositoryRoot + " is not a git repository");
    }
  }

  private int getCommitCount(Git gitClient) throws IOException, GitAPIException {
    ObjectId head = gitClient.getRepository().resolve(Constants.HEAD);
    Iterable<RevCommit> iterator = gitClient.log().add(head).call();
    Counter count = new Counter(0);
    iterator.forEach(commit -> count.increment());
    return count.getValue();
  }

  private boolean shouldBeProcessed(VcsCommit commit, Collection<CommitFilter> filters) {
    for (CommitFilter filter : filters) {
      if (!filter.shouldBeProcessed(commit)) {
        return false;
      }
    }
    return true;
  }
}
