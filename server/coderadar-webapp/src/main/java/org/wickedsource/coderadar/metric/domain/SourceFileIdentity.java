package org.wickedsource.coderadar.metric.domain;

import javax.persistence.*;

@Entity
@Table
public class SourceFileIdentity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
