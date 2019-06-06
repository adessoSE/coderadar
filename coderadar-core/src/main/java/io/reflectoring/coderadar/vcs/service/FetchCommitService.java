package io.reflectoring.coderadar.vcs.service;

import io.reflectoring.coderadar.vcs.port.driver.walk.fetchCommit.FetchCommitCommand;
import io.reflectoring.coderadar.vcs.port.driver.walk.fetchCommit.FetchCommitUseCase;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.stereotype.Service;

@Service("FetchCommitService")
public class FetchCommitService implements FetchCommitUseCase {

  @Override
  public void fetchCommit(FetchCommitCommand command) {
    try {
      // cloning repository without content
      Git gitClient =
          Git.cloneRepository()
              .setNoCheckout(true)
              // TODO .setDirectory(command.getLocalDir().toFile())
              .setURI(command.getUrl().toString())
              .call();

      // checking out the specified commit
      gitClient.checkout().setName(command.getCommitName()).call();

      gitClient.getRepository().close();
    } catch (GitAPIException e) {
      /*TODO throw new IllegalStateException(
            String.format(
                "error while fetching commit into directory %s from url %s",
                command.getLocalDir(), command.getUrl().toString()),
            e);
      }*/
    }
  }
}
