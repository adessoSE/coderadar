package org.wickedsource.coderadar.metric.domain.metricvalue;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.file.domain.File;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class MetricValueId implements Serializable {

  @ManyToOne private Commit commit;

  @ManyToOne private File file;

  @Column private String metricName;

  public MetricValueId() {}

  public MetricValueId(Commit commit, File file, String metricName) {
    this.commit = commit;
    this.file = file;
    this.metricName = metricName;
  }

  public Commit getCommit() {
    return commit;
  }

  public void setCommit(Commit commit) {
    this.commit = commit;
  }

  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
  }

  public String getMetricName() {
    return metricName;
  }

  public void setMetricName(String metricName) {
    this.metricName = metricName;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof MetricValueId)) {
      return false;
    }
    MetricValueId that = (MetricValueId) obj;
    return this.metricName.equals(that.metricName)
        && this.commit.getId().equals(that.commit.getId())
        && this.file.getId().equals(that.file.getId());
  }

  @Override
  public int hashCode() {
    return 17 + metricName.hashCode() + commit.getId().hashCode() + file.getId().hashCode();
  }

  @Override
  public String toString() {
    return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
