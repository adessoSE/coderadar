package org.wickedsource.coderadar.vcs.git;

import java.io.File;

public class GitRepositoryClonerManualTest {

  public static void main(String[] args) {
    GitRepositoryCloner cloner = new GitRepositoryCloner();
    cloner.cloneRepository("https://github.com/thombergs/coderadar.git", new File("C:\\coderadar"));
  }
}
