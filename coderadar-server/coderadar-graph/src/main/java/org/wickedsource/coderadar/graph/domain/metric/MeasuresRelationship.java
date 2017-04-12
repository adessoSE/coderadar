package org.wickedsource.coderadar.graph.domain.metric;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import org.wickedsource.coderadar.graph.domain.filesnapshot.FileSnapshotNode;

@RelationshipEntity(type = "MEASURES")
public class MeasuresRelationship {

  @GraphId private Long id;

  @StartNode private MetricNode metric;

  @EndNode private FileSnapshotNode fileSnapshot;

  private int value;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public MetricNode getMetric() {
    return metric;
  }

  public void setMetric(MetricNode metric) {
    this.metric = metric;
  }

  public FileSnapshotNode getFileSnapshot() {
    return fileSnapshot;
  }

  public void setFileSnapshot(FileSnapshotNode fileSnapshot) {
    this.fileSnapshot = fileSnapshot;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }
}
