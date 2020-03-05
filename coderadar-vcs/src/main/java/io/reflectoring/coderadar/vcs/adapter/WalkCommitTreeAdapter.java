package io.reflectoring.coderadar.vcs.adapter;

import io.reflectoring.coderadar.vcs.UnableToWalkCommitTreeException;
import io.reflectoring.coderadar.vcs.port.driven.WalkCommitTreePort;
import io.reflectoring.coderadar.vcs.port.driven.WalkTreeCommandInterface;
import java.io.File;
import java.io.IOException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.springframework.stereotype.Service;

@Service
public class WalkCommitTreeAdapter implements WalkCommitTreePort {
  @Override
  public void walkCommitTree(
      String projectRoot, String name, WalkTreeCommandInterface commandInterface)
      throws UnableToWalkCommitTreeException {
    try {
      Git git = Git.open(new File(projectRoot));
      ObjectId commitId = git.getRepository().resolve(name);

      RevWalk walk = new RevWalk(git.getRepository());
      RevCommit commit = walk.parseCommit(commitId);
      RevTree tree = commit.getTree();
      TreeWalk treeWalk = new TreeWalk(git.getRepository());
      treeWalk.addTree(tree);
      treeWalk.setRecursive(true);

      while (treeWalk.next()) {
        if (!treeWalk.getPathString().endsWith(".java")
            || treeWalk.getPathString().contains("build")
            || treeWalk.getPathString().contains("out")
            || treeWalk.getPathString().contains("classes")
            || treeWalk.getPathString().contains("node_modules")
            || treeWalk.getPathString().contains("test")) {
          continue;
        }
        commandInterface.walkMethod(treeWalk.getPathString());
      }
      git.close();
    } catch (IOException e) {
      throw new UnableToWalkCommitTreeException(e.getMessage());
    }
  }
}
