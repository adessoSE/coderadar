package org.wickedsource.coderadar.qualityprofile.domain;

import org.wickedsource.coderadar.project.domain.Project;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class QualityProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "profile")
    private List<QualityProfileMetric> metrics = new ArrayList<>();

    @Column
    private String name;

    @ManyToOne
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
