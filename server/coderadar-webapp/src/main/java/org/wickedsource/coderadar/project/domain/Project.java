package org.wickedsource.coderadar.project.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

@Entity
@Table
public class Project {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Embedded
    private VcsCoordinates vcsCoordinates;

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

    public VcsCoordinates getVcsCoordinates() {
        return vcsCoordinates;
    }

    public void setVcsCoordinates(VcsCoordinates vcsCoordinates) {
        this.vcsCoordinates = vcsCoordinates;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
