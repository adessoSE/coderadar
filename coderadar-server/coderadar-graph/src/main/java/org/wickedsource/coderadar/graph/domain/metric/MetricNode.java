package org.wickedsource.coderadar.graph.domain.metric;

import java.util.Set;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class MetricNode {

  @GraphId private Long id;

  private String metricName;

  private MetricAggregationType aggregationType;

  @Relationship(type = "MEASURES")
  private Set<MeasuresRelationship> measures;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMetricName() {
    return metricName;
  }

  public void setMetricName(String metricName) {
    this.metricName = metricName;
  }

  public MetricAggregationType getAggregationType() {
    return aggregationType;
  }

  public void setAggregationType(MetricAggregationType aggregationType) {
    this.aggregationType = aggregationType;
  }

  public Set<MeasuresRelationship> getMeasures() {
    return measures;
  }

  public void measures(Set<MeasuresRelationship> measures) {
    this.measures = measures;
  }
}
