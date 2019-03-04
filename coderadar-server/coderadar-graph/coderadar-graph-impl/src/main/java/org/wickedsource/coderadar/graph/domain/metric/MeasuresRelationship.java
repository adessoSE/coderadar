package org.wickedsource.coderadar.graph.domain.metric;

import lombok.Data;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import org.wickedsource.coderadar.graph.domain.filesnapshot.FileSnapshotNode;

@RelationshipEntity(type = "MEASURES")
@Data
public class MeasuresRelationship {

  @GraphId private Long id;

  @StartNode private MetricNode metric;

  @EndNode private FileSnapshotNode fileSnapshot;

  private int value;
}
