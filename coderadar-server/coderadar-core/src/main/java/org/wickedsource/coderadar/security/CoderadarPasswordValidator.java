package org.wickedsource.coderadar.security;

import com.google.common.base.Joiner;
import java.util.Arrays;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.passay.*;

/** Validator for user passwords. */
public class CoderadarPasswordValidator implements ConstraintValidator<ValidPassword, String> {

	@Override
	public void initialize(ValidPassword constraintAnnotation) {}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
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
