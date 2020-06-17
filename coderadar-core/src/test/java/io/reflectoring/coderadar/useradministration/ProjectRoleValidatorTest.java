package io.reflectoring.coderadar.useradministration;

import io.reflectoring.coderadar.useradministration.domain.ProjectRole;
import io.reflectoring.coderadar.useradministration.domain.ProjectRolePattern;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProjectRoleValidatorTest {
  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  void acceptAdminRole() {
    TestDto testDto = new TestDto(ProjectRole.ADMIN);

    Set<ConstraintViolation<TestDto>> result = validator.validate(testDto);
    Assertions.assertThat(result).isEmpty();
  }

  @Test
  void acceptMemberRole() {
    TestDto testDto = new TestDto(ProjectRole.MEMBER);

    Set<ConstraintViolation<TestDto>> result = validator.validate(testDto);
    Assertions.assertThat(result).isEmpty();
  }

  static class TestDto {
    @ProjectRolePattern private final ProjectRole projectRole;

    TestDto(ProjectRole role) {
      this.projectRole = role;
    }
  }
}
