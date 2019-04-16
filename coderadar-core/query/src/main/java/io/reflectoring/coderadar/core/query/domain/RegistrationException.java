package io.reflectoring.coderadar.core.query.domain;

public class RegistrationException extends UserException {

    public RegistrationException(String username) {
        super(String.format("User %s is already registered", username));
    }
}
