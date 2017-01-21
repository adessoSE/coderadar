package org.wickedsource.coderadar.security;

import com.google.common.base.Joiner;
import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

/**
 * Validator for password users define while registration or changing password
 */
public class CoderadarPasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        PasswordValidator passwordValidator = new PasswordValidator(//
                                                                           Arrays.asList(
                        // at least one digit character
                        new CharacterRule(EnglishCharacterData.Digit, 1),

                                                                                   new CharacterRule(EnglishCharacterData.Alphabetical, 1),

                        // no whitespace
                        new WhitespaceRule()));
        RuleResult result = passwordValidator.validate(new PasswordData(value));
        if (result.isValid()) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(Joiner.on("n").join(passwordValidator.getMessages(result))).addConstraintViolation();
        return false;
    }
}
