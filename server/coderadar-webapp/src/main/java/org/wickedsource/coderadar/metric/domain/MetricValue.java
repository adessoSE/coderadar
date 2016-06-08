package org.wickedsource.coderadar.metric.domain;

import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.file.domain.File;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class MetricValue {

    @EmbeddedId
    private MetricValueId id;

    @Column
    private Long value;

    public MetricValue() {
    }

    public MetricValue(MetricValueId id, Long value) {
        this.id = id;
        this.value = value;
    }

    public Commit getCommit(){
        return id.getCommit();
    }

    public File getFile(){
        return id.getFile();
    }

    public String getMetricName(){
        return id.getMetricName();
    }

    public MetricValueId getId() {
        return id;
    }

    public void setId(MetricValueId id) {
        this.id = id;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
