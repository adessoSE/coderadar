package io.reflectoring.coderadar.useradministration.domain;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ProjectRoleValidator.class)
public @interface ProjectRolePattern {
  ProjectRole[] anyOf() default {ProjectRole.ADMIN, ProjectRole.MEMBER};

  String message() default "must be any of {anyOf}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
