package org.wickedsource.coderadar.graph.domain.file;

import java.util.HashSet;
import java.util.Set;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import org.wickedsource.coderadar.graph.domain.commit.CommitNode;

@NodeEntity(label = "File")
public class FileNode {

  @GraphId private Long id;

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

  public FileNode() {}

  public FileNode(FileId id) {
    this.fileId = id;
  }

  public FileId getFileId() {
    return fileId;
  }

  public void setFileId(FileId fileId) {
    this.fileId = fileId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public CommitNode getAddedIn() {
    return addedIn;
  }

  public void addedIn(CommitNode commit) {
    this.addedIn = commit;
  }

  public Set<CommitNode> getModifiedIn() {
    return modifiedIn;
  }

  public void modifiedIn(CommitNode commit) {
    this.modifiedIn.add(commit);
  }

  public CommitNode getDeletedIn() {
    return deletedIn;
  }

  public void deletedIn(CommitNode commit) {
    this.deletedIn = commit;
  }

  public Set<CommitNode> getRenamedIn() {
    return renamedIn;
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
