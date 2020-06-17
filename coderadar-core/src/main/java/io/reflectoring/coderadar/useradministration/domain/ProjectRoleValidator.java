package io.reflectoring.coderadar.useradministration.domain;

import java.util.Arrays;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ProjectRoleValidator implements ConstraintValidator<ProjectRolePattern, ProjectRole> {
  private ProjectRole[] projectRoles;

  @Override
  public void initialize(ProjectRolePattern constraintAnnotation) {
    this.projectRoles = constraintAnnotation.anyOf();
  }

  @Override
  public boolean isValid(ProjectRole value, ConstraintValidatorContext context) {
    return value == null || Arrays.asList(projectRoles).contains(value);
  }
}
