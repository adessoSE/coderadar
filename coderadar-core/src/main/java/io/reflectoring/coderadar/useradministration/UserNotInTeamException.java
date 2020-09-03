package io.reflectoring.coderadar.useradministration;

public class UserNotInTeamException extends RuntimeException {
  public UserNotInTeamException(Long userId, long teamId) {
    super(String.format("User with id %d is not in team with id %d", userId, teamId));
  }
}
