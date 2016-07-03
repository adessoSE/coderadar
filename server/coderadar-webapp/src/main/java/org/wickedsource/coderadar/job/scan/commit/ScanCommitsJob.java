package org.wickedsource.coderadar.job.scan.commit;

import org.wickedsource.coderadar.job.core.Job;

import javax.persistence.Entity;

/**
 * A Job that scans all commits of a VCS and stores metadata about them in the database.
 */
@Entity
public class ScanCommitsJob extends Job {

}
