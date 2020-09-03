package io.reflectoring.coderadar.useradministration;

public class TeamNotAssignedException extends RuntimeException {
  public TeamNotAssignedException(Long projectId, Long teamId) {
    super(
        String.format("Team with id %d is not assigned to project with id %d", teamId, projectId));
  }
}
