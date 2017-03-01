package org.wickedsource.coderadar.qualityprofile.domain;

import javax.persistence.*;

@Entity
@Table(name = "quality_profile_metric")
@SequenceGenerator(
  name = "quality_profile_metric_sequence",
  sequenceName = "seq_qpme_id",
  allocationSize = 1
)
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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public MetricType getMetricType() {
    return metricType;
  }

  public void setMetricType(MetricType metricType) {
    this.metricType = metricType;
  }

  public QualityProfile getProfile() {
    return profile;
  }

  public void setProfile(QualityProfile profile) {
    this.profile = profile;
  }
}
