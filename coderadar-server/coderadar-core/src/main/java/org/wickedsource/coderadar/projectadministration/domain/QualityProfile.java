package org.wickedsource.coderadar.projectadministration.domain;

import lombok.Data;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.qualityprofile.domain.QualityProfileMetric;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quality_profile")
@SequenceGenerator(
  name = "quality_profile_sequence",
  sequenceName = "seq_qupr_id",
  allocationSize = 1
)
@Data
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
}
