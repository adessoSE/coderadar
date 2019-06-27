package io.reflectoring.coderadar.projectadministration.port.driver.user;

import com.google.common.base.Joiner;
import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

/** Validator for user passwords. */
public class CoderadarPasswordValidator implements ConstraintValidator<ValidPassword, String> {

  @Override
  public void initialize(ValidPassword constraintAnnotation) {}

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null || value.isEmpty()) {
      return false;
    }
    PasswordValidator passwordValidator =
        new PasswordValidator( //
            Arrays.asList(
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Alphabetical, 1),
                new WhitespaceRule()));
    RuleResult result = passwordValidator.validate(new PasswordData(value));
    if (result.isValid()) {
      return true;
    }
    context.disableDefaultConstraintViolation();
    context
        .buildConstraintViolationWithTemplate(
            Joiner.on("n").join(passwordValidator.getMessages(result)))
        .addConstraintViolation();
    return false;
  }
}
