package org.wickedsource.coderadar.commit.domain;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.wickedsource.coderadar.file.domain.File;

@Embeddable
@NoArgsConstructor
@Data
@AllArgsConstructor
public class CommitToFileId implements Serializable {

  @ManyToOne
  @JoinColumn(name = "commit_id")
  private Commit commit;

  @ManyToOne
  @JoinColumn(name = "file_id")
  private File file;

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof CommitToFileId)) {
      return false;
    }
    CommitToFileId that = (CommitToFileId) obj;
    return this.commit.getId().equals(that.commit.getId())
        && this.file.getId().equals(that.file.getId());
  }

  @Override
  public int hashCode() {
    return 17 + commit.getId().hashCode() + file.getId().hashCode();
  }

  @Override
  public String toString() {
    return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
