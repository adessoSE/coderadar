package io.reflectoring.coderadar.useradministration.domain;

/**
 * ADMIN: - Can delete a project - Can remove users from a team - Can add users to a team - Can
 * analyze a project - Can delete analysis results - Can configure file patterns, modules,
 * analyzers, (merge contributors?)
 *
 * <p>MEMBER: - Can analyze a project.
 */
public enum ProjectRole {
  ADMIN("admin"),
  MEMBER("member");

  private final String value;

  ProjectRole(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
