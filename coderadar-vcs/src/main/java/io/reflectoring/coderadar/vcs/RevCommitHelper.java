package io.reflectoring.coderadar.vcs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public class RevCommitHelper {

  private RevCommitHelper() {}

  public static List<RevCommit> getRevCommits(String repositoryRoot) {
    try (Git git = Git.open(new File(repositoryRoot))) {
      List<RevCommit> revCommits = new ArrayList<>();
      RevWalk revWalk = new RevWalk(git.getRepository());
      for (Ref ref : git.branchList().setListMode(ListBranchCommand.ListMode.ALL).call()) {
        revWalk.markStart(revWalk.parseCommit(ref.getObjectId()));
        revWalk
            .iterator()
            .forEachRemaining(
                revCommit -> {
                  if (revCommits.stream()
                      .noneMatch(revCommit1 -> revCommit1.getId().equals(revCommit.getId()))) {
                    revCommits.add(revCommit);
                  }
                });
        revWalk.reset();
      }
      return revCommits;
    } catch (Exception e) {
      throw new IllegalStateException(
          String.format("Error accessing git repository at %s", repositoryRoot), e);
    }
  }
}
