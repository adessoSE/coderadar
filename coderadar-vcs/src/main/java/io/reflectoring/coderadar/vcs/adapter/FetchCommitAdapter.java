package io.reflectoring.coderadar.vcs.adapter;

import io.reflectoring.coderadar.vcs.UnableToFetchCommitException;
import io.reflectoring.coderadar.vcs.port.driven.FetchCommitPort;
import io.reflectoring.coderadar.vcs.port.driver.fetchcommit.FetchCommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.stereotype.Service;

@Service
public class FetchCommitAdapter implements FetchCommitPort {

  @Override
  public void fetchCommit(FetchCommitCommand command) throws UnableToFetchCommitException {
    try {
      // cloning repository without content
      Git gitClient =
          Git.cloneRepository()
              .setNoCheckout(true)
              .setDirectory(command.getLocalDir().toFile())
              .setURI(command.getUrl().toString())
              .call();

      // checking out the specified commit
      gitClient.checkout().setName(command.getCommitName()).call();

      gitClient.getRepository().close();
    } catch (GitAPIException e) {
      throw new UnableToFetchCommitException(
          String.format(
              "Error while fetching commit into directory %s from url %s",
              command.getLocalDir(), command.getUrl().toString()));
    }
  }
}
