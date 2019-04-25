package io.reflectoring.coderadar.core.analyzer.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "finding")
@Data
public class Finding {

  @EmbeddedId private MetricValueId id;

  @Column(name = "line_start")
  private Integer lineStart;

  @Column(name = "line_end")
  private Integer lineEnd;

  @Column(name = "char_start")
  private Integer charStart;

  @Column(name = "char_end")
  private Integer charEnd;
}
