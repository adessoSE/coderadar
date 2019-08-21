package io.reflectoring.coderadar.graph.analyzer.domain;

import java.util.LinkedList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NodeEntity
@EqualsAndHashCode
public class MetricValueEntity {
  private Long id;
  private String name;
  private Long value;

  @Relationship(type = "VALID_FOR")
  private CommitEntity commit;

  @Relationship(type = "LOCATED_IN")
  @EqualsAndHashCode.Exclude
  private List<FindingEntity> findings = new LinkedList<>();

  @Relationship(type = "MEASURED_BY", direction = Relationship.INCOMING)
  @EqualsAndHashCode.Exclude
  private FileEntity file;
}
