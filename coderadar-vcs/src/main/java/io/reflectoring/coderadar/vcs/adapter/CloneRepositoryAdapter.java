package io.reflectoring.coderadar.vcs.adapter;

import io.reflectoring.coderadar.vcs.UnableToCloneRepositoryException;
import io.reflectoring.coderadar.vcs.port.driven.CloneRepositoryPort;
import io.reflectoring.coderadar.vcs.port.driver.clone.CloneRepositoryCommand;
import java.io.IOException;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Service;

@Service
public class CloneRepositoryAdapter implements CloneRepositoryPort {

  @Override
  public void cloneRepository(CloneRepositoryCommand cloneRepositoryCommand)
      throws UnableToCloneRepositoryException {
    try {
      // TODO: support progress monitoring
      Git.cloneRepository()
          .setURI(cloneRepositoryCommand.getRemoteUrl())
          .setDirectory(cloneRepositoryCommand.getLocalDir())
          .call()
          .close();
    } catch (GitAPIException e) {
      try {
        FileUtils.deleteDirectory(cloneRepositoryCommand.getLocalDir());
        Git.cloneRepository()
            .setCredentialsProvider(
                new UsernamePasswordCredentialsProvider(
                    cloneRepositoryCommand.getUsername(), cloneRepositoryCommand.getPassword()))
            .setURI(cloneRepositoryCommand.getRemoteUrl())
            .setDirectory(cloneRepositoryCommand.getLocalDir())
            .call()
            .close();
      } catch (GitAPIException | IOException ex) {
        throw new UnableToCloneRepositoryException(e.getMessage());
      }
    }
  }
}
