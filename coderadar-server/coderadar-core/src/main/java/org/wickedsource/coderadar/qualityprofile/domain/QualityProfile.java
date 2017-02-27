package org.wickedsource.coderadar.qualityprofile.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import org.wickedsource.coderadar.project.domain.Project;

@Entity
@Table(name = "quality_profile")
@SequenceGenerator(name = "quality_profile_sequence", sequenceName = "seq_qupr_id")
public class QualityProfile {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quality_profile_sequence")
  @Column(name = "id")
  private Long id;

  @OneToMany(
    fetch = FetchType.EAGER,
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    mappedBy = "profile"
  )
  private List<QualityProfileMetric> metrics = new ArrayList<>();

  @Column(name = "name")
  private String name;

  @ManyToOne
  @JoinColumn(name = "project_id")
  private Project project;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<QualityProfileMetric> getMetrics() {
    return metrics;
  }

  public void setMetrics(List<QualityProfileMetric> metrics) {
    this.metrics = metrics;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }
}
