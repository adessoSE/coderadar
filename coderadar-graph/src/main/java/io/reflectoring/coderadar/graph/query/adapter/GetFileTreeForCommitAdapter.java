package io.reflectoring.coderadar.graph.query.adapter;

import io.reflectoring.coderadar.graph.query.repository.MetricQueryRepository;
import io.reflectoring.coderadar.query.domain.FileTree;
import io.reflectoring.coderadar.query.port.driven.GetFileTreeForCommitPort;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetFileTreeForCommitAdapter implements GetFileTreeForCommitPort {

  private final MetricQueryRepository metricQueryRepository;

  @Override
  public FileTree getFileTreeForCommit(long projectId, long commitHash, boolean changeFilesOnly) {
    List<String> filepaths;
    if (changeFilesOnly) {
      filepaths = metricQueryRepository.getFilesChangedInCommit(projectId, commitHash);
    } else {
      filepaths = metricQueryRepository.getFileTreeForCommit(projectId, commitHash);
    }

    FileTree fileTree = new FileTree("/", new ArrayList<>());
    for (String filepath : filepaths) {
      addToTree(fileTree, Arrays.asList(filepath.split("/")));
    }
    mergeSinglePaths(fileTree);
    sortTree(fileTree);
    return fileTree;
  }

  private void sortTree(FileTree fileTree) {
    if (fileTree.getChildren() != null) {
      fileTree
          .getChildren()
          .sort(
              (tree, tree2) -> {
                if (tree.getChildren() == null && tree2.getChildren() != null) {
                  return 1;
                } else if (tree.getChildren() != null && tree2.getChildren() == null) {
                  return -1;
                } else {
                  return tree.getPath().compareToIgnoreCase(tree2.getPath());
                }
              });
      for (FileTree child : fileTree.getChildren()) {
        sortTree(child);
      }
    }
  }

  private void mergeSinglePaths(FileTree fileTree) {
    if (fileTree.getChildren() != null) {
      for (FileTree child : fileTree.getChildren()) {
        mergeSinglePaths(child);
        while (child.getChildren() != null && child.getChildren().size() == 1) {
          child.setPath(child.getPath() + "/" + child.getChildren().get(0).getPath());
          child.setChildren(child.getChildren().get(0).getChildren());
        }
      }
    }
  }

  private void addToTree(FileTree tree, List<String> path) {
    if (tree.getChildren() == null) {
      tree.setChildren(new ArrayList<>());
    }
    for (FileTree child : tree.getChildren()) {
      if (child.getPath().equals(path.get(0))) {
        addToTree(child, path.subList(1, path.size()));
        return;
      }
    }
    tree.getChildren().add(new FileTree(path.get(0), null));
    if (path.size() > 1) {
      addToTree(tree, path);
    }
  }
}
