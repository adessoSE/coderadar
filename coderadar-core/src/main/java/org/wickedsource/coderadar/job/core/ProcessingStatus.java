package org.wickedsource.coderadar.job.core;

/** The status of a Job. */
public enum ProcessingStatus {

  /** The Job is queued and currently waiting to be executed. */
  WAITING,

  /** The Job is currently running. */
  PROCESSING,

  /** The Job is finished. This status does not define if the Job was successful. */
  PROCESSED
}
