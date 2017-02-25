package org.wickedsource.coderadar.job.associate;

import javax.persistence.Entity;
import org.wickedsource.coderadar.job.core.Job;

/**
 * A Job that refines the {@link org.wickedsource.coderadar.file.domain.GitLogEntry}s that contain
 * metadata about the files of a VCS and merges renamed files to a single FileIdentity.
 */
@Entity
public class AssociateGitLogJob extends Job {}
