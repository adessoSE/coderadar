package io.reflectoring.coderadar.vcs.adapter;

import io.reflectoring.coderadar.vcs.port.driven.DeleteLocalRepositoryPort;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Service
public class DeleteLocalRepositoryAdapter implements DeleteLocalRepositoryPort {

  /**
   * Closes the Jgit repository and deletes the local repository.
   *
   * @param repositoryPath Path to the repository.
   * @throws IOException Thrown if the repository cannot be deleted
   */
  @Override
  public void deleteRepository(String repositoryPath) throws IOException {
    FileRepositoryBuilder builder = new FileRepositoryBuilder();
    Repository repository = builder.setWorkTree(Paths.get(repositoryPath).toFile()).build();
    Git git = new Git(repository);
    git.getRepository().close();
    deleteDir(repositoryPath);
  }

  private void deleteDir(String path) throws IOException {
    FileUtils.deleteDirectory(new File(path));
  }
}
