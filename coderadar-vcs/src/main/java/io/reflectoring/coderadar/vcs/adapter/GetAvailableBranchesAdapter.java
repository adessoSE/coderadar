package io.reflectoring.coderadar.vcs.adapter;

import io.reflectoring.coderadar.projectadministration.domain.Branch;
import io.reflectoring.coderadar.vcs.port.driven.GetAvailableBranchesPort;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.springframework.stereotype.Service;

@Service
public class GetAvailableBranchesAdapter implements GetAvailableBranchesPort {

  @Override
  public List<Branch> getAvailableBranches(File repositoryRoot) {
    try (Git git = Git.open(repositoryRoot)) {
      return getBranches(git);
    } catch (Exception e) {
      throw new IllegalStateException(
          String.format("Error accessing git repository at %s", repositoryRoot), e);
    }
  }

  private List<Branch> getBranches(Git git) throws GitAPIException {
    List<Branch> result = new ArrayList<>();
    for (Ref ref : git.branchList().setListMode(ListBranchCommand.ListMode.ALL).call()) {
      String[] branchName = ref.getName().split("/");
      int length = branchName.length;
      String truncatedName = branchName[length - 1];
      if (result.stream().noneMatch(branch -> branch.getName().equals(truncatedName))) {
        result.add(
            new Branch()
                .setName(branchName[length - 1])
                .setCommitHash(ref.getObjectId().getName()));
      }
    }
    return result;
  }
}

