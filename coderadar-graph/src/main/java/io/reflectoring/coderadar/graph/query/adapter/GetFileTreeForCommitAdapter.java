package io.reflectoring.coderadar.graph.query.adapter;

import io.reflectoring.coderadar.graph.query.repository.MetricQueryRepository;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.query.domain.FileTree;
import io.reflectoring.coderadar.query.port.driven.GetFileTreeForCommitPort;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
public class GetFileTreeForCommitAdapter implements GetFileTreeForCommitPort {

  private final MetricQueryRepository metricQueryRepository;

  public GetFileTreeForCommitAdapter(MetricQueryRepository metricQueryRepository) {
    this.metricQueryRepository = metricQueryRepository;
  }

  @Override
  public FileTree getFileTreeForCommit(
      long projectId, String commitHash, List<FilePattern> filePatterns) {
    Pair<List<String>, List<String>> includesAndExcludes =
        PatternUtil.mapPatternsToRegex(filePatterns);
    List<String> filepaths =
        metricQueryRepository.getFileTreeForCommit(
            projectId, commitHash);
    FileTree fileTree = new FileTree("/", new ArrayList<>());
    for (String filepath : filepaths) {
      addToTree(fileTree, Arrays.asList(filepath.split("/")));
    }
    mergeSinglePaths(fileTree);
    return fileTree;
  }

  private void mergeSinglePaths(FileTree fileTree) {
    if(fileTree.getChildren() != null) {
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
    boolean found = false;
    if (tree.getChildren() == null) {
      tree.setChildren(new ArrayList<>());
    }
    for (FileTree child : tree.getChildren()) {
      if (child.getPath().equals(path.get(0))) {
        found = true;
        addToTree(child, path.subList(1, path.size()));
      }
    }
    if (!found) {
      tree.getChildren().add(new FileTree(path.get(0), null));
      if (path.size() > 1) {
        addToTree(tree, path);
      }
    }
  }
}
