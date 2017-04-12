package org.wickedsource.coderadar.graph.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.graph.domain.commit.CommitNode;
import org.wickedsource.coderadar.graph.domain.file.FileId;
import org.wickedsource.coderadar.graph.domain.file.FileNode;
import org.wickedsource.coderadar.graph.domain.filesnapshot.FileSnapshotNode;

@Service
public class FileSnapshotNodeFactory {

  void appendFileSnapshotNodes(CommitNode commitNode, List<GitChange> gitChanges) {
    for (GitChange change : gitChanges) {

      // create FileSnapshot
      FileSnapshotNode fileSnapshotNode = new FileSnapshotNode();
      fileSnapshotNode.setFilepath(change.getFilepath());
      fileSnapshotNode.snapshotInCommit(commitNode);
      commitNode.touched(fileSnapshotNode);

      // add new File
      if (change.getChangeType() == GitChange.ChangeType.ADDED) {
        FileNode fileNode = new FileNode();
        fileNode.setFileId(FileId.newId());
        fileNode.addedIn(commitNode);
        fileSnapshotNode.snapshotOfFile(fileNode);
      }
    }
  }
}
