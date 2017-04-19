package org.wickedsource.coderadar.graph.domain.commit;

import static org.neo4j.ogm.annotation.Relationship.OUTGOING;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import org.wickedsource.coderadar.graph.domain.file.FileNode;
import org.wickedsource.coderadar.graph.domain.filesnapshot.FileSnapshotNode;

@NodeEntity(label = "Commit")
public class CommitNode {

  @GraphId private Long id;

  @Relationship(type = "IS_CHILD_OF", direction = OUTGOING)
  private Set<CommitNode> parents = new HashSet<>();

  @Relationship(type = "TOUCHED")
  private Set<FileNode> touchedFiles = new HashSet<>();

  @Relationship(type = "TOUCHED")
  private Set<FileSnapshotNode> touchedFileSnapshots = new HashSet<>();

  @Convert(CommitNameConverter.class)
  @Index(unique = true, primary = true)
  private CommitName name;

  @Convert(LocalDateTimeConverter.class)
  private LocalDateTime timestamp;

  public CommitNode(Set<CommitNode> parents, CommitName name, LocalDateTime timestamp) {
    this.parents = parents;
    this.name = name;
    this.timestamp = timestamp;
  }

  public CommitNode(CommitNode parent, CommitName name, LocalDateTime timestamp) {
    this.parents.add(parent);
    this.name = name;
    this.timestamp = timestamp;
  }

  public CommitNode(CommitName name, LocalDateTime timestamp) {
    this.name = name;
    this.timestamp = timestamp;
  }

  public CommitNode() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void isChildOf(CommitNode parent) {
    this.parents.add(parent);
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public Set<CommitNode> getParents() {
    return parents;
  }

  public void setParents(Set<CommitNode> parents) {
    this.parents = parents;
  }

  public CommitName getName() {
    return name;
  }

  public void setName(CommitName name) {
    this.name = name;
  }

  public void touched(FileNode fileNode) {
    this.touchedFiles.add(fileNode);
  }

  public void touched(FileSnapshotNode fileSnapshotNode) {
    this.touchedFileSnapshots.add(fileSnapshotNode);
  }

  public Set<FileNode> getTouchedFiles() {
    return touchedFiles;
  }

  public Set<FileSnapshotNode> getTouchedFileSnapshots() {
    return touchedFileSnapshots;
  }

  @Override
  public String toString() {
    return String.format("CommitNode[id=%d; name=%s]", this.id, this.name);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    CommitNode that = (CommitNode) o;

    return name.equals(that.name);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }
}
