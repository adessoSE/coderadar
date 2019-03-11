package org.wickedsource.coderadar.graph.domain.file;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import org.wickedsource.coderadar.graph.domain.commit.CommitNode;

@NodeEntity(label = "File")
@Getter
@NoArgsConstructor
public class FileNode {

  @Setter @GraphId private Long id;

  @Setter
  @Convert(FileIdConverter.class)
  private FileId fileId;

  @Relationship(type = "ADDED_IN_COMMIT")
  private CommitNode addedIn;

  @Relationship(type = "MODIFIED_IN_COMMIT")
  private Set<CommitNode> modifiedIn = new HashSet<>();

  @Relationship(type = "DELETED_IN_COMMIT")
  private CommitNode deletedIn;

  @Relationship(type = "RENAMED_IN_COMMIT")
  private Set<CommitNode> renamedIn = new HashSet<>();

  public FileNode(FileId id) {
    this.fileId = id;
  }

  public void addedIn(CommitNode commit) {
    this.addedIn = commit;
  }

  public void modifiedIn(CommitNode commit) {
    this.modifiedIn.add(commit);
  }

  public void deletedIn(CommitNode commit) {
    this.deletedIn = commit;
  }

  public void renamedIn(CommitNode commit) {
    this.renamedIn.add(commit);
  }

  @Override
  public String toString() {
    return String.format("FileNode[fileId=%s]", this.fileId);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    FileNode fileNode = (FileNode) o;

    return fileId.equals(fileNode.fileId);
  }

  @Override
  public int hashCode() {
    return fileId.hashCode();
  }
}
