package org.wickedsource.coderadar.module.domain;

import org.wickedsource.coderadar.project.domain.Project;

import javax.persistence.*;

/**
 * The codebase may be organized into modules, each module starting at a certain path.
 * All files within that path are considered to be part of the module.
 */
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"project_id", "path"})})
public class Module {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "path")
    private String path;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
