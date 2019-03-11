package org.wickedsource.coderadar.graph.domain.metric;

import java.util.Set;
import lombok.Data;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
@Data
public class MetricNode {

  @GraphId private Long id;

  private String metricName;

  private MetricAggregationType aggregationType;

  @Relationship(type = "MEASURES")
  private Set<MeasuresRelationship> measures;
}
