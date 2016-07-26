package org.wickedsource.coderadar.metric.domain.qualityprofile;

import javax.persistence.*;

@Entity
@Table
public class QualityProfileMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private MetricType metricType;

    @ManyToOne
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