package io.reflectoring.coderadar.useradministration;

public class UserNotAssignedException extends RuntimeException {
  public UserNotAssignedException(Long projectId, Long userId) {
    super(
        String.format("User with id %d is not assigned to project with id %d", userId, projectId));
  }
}
