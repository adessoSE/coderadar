package org.wickedsource.coderadar.job.analyze;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.job.core.Job;

/** A Job that analyzes a single commit. */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class AnalyzeCommitJob extends Job {

  @ManyToOne private Commit commit;
}
