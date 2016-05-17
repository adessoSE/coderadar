package org.wickedsource.coderadar.projectfiles.domain;

import org.wickedsource.coderadar.project.domain.FileType;
import org.wickedsource.coderadar.project.domain.InclusionType;
import org.wickedsource.coderadar.project.domain.Project;

import javax.persistence.*;

@Entity
@Table
public class ProjectFiles {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String pattern;

    @Column
    private InclusionType inclusionType;

    @Column
    private FileType fileType;

    @ManyToOne
    private Project project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Ant-style file path pattern.
     */
    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public InclusionType getInclusionType() {
        return inclusionType;
    }

    public void setInclusionType(InclusionType inclusionType) {
        this.inclusionType = inclusionType;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

}
