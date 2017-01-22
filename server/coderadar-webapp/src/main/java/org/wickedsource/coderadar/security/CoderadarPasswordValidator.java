package org.wickedsource.coderadar.security;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.passay.*;

import com.google.common.base.Joiner;

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
                Arrays.asList(new LengthRule(8, 20), //
                        // at least one upper-case character
                        new CharacterRule(EnglishCharacterData.UpperCase, 1),

                        // at least one lower-case character
                        new CharacterRule(EnglishCharacterData.LowerCase, 1),

                        // at least one digit character
                        new CharacterRule(EnglishCharacterData.Digit, 1),

                        // at least one symbol (special character)
                        new CharacterRule(EnglishCharacterData.Special, 1),

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
