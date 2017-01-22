package org.wickedsource.coderadar.job.merge;

import javax.persistence.Entity;
import org.wickedsource.coderadar.job.core.Job;

/**
 * A Job that refines the CommitLogEntries that contain metadata about the files of a VCS and merges
 * renamed files to a single FileIdentity.
 */
@Entity
public class MergeLogJob extends Job {}
