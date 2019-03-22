package org.wickedsource.coderadar.qualityprofile.domain;

import javax.persistence.*;
import lombok.Data;
import org.wickedsource.coderadar.projectadministration.domain.QualityProfile;

@Entity
@Table(name = "quality_profile_metric")
@SequenceGenerator(
  name = "quality_profile_metric_sequence",
  sequenceName = "seq_qpme_id",
  allocationSize = 1
)
@Data
public class QualityProfileMetric {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quality_profile_metric_sequence")
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "metric_type")
  @Enumerated(EnumType.STRING)
  private MetricType metricType;

  @ManyToOne
  @JoinColumn(name = "profile_id")
  private QualityProfile profile;
}
