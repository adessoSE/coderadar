package io.reflectoring.coderadar.projectadministration.port.driven.project;

public interface ProjectStatusPort {
  boolean isBeingProcessed(Long projectId);

  void setBeingProcessed(Long projectId, boolean isBeingProcessed);
}
