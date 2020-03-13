package io.reflectoring.coderadar.projectadministration.port.driven.project;

public interface ProjectStatusPort {

  /**
   * @param projectId The id of the project to check.
   * @return True if the project is being processed, false otherwise.
   */
  boolean isBeingProcessed(long projectId);

  /**
   * @param projectId The id of the project, whose status to check
   * @param isBeingProcessed The processing status to set.
   */
  void setBeingProcessed(long projectId, boolean isBeingProcessed);
}
