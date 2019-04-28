package io.reflectoring.coderadar.core.analyzer.domain;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * A job that defines which commits are to be analyzed. Storing an entity of this type in the
 * database automatically triggers analysis of a project's code base.
 */
@NodeEntity
@Data
public class AnalyzingJob {
  private Long id;

  /**
   * The date from which to start scanning commits. If null, all commits from the very beginning are
   * analyzed.
   */
  private Date fromDate; // TODO: A date converter should be used here.

  /**
   * If set to false, no new commits will be analyzed for the project. Analyses that are already
   * queued will be performed, however.
   */
  private boolean active = false;
}
