package io.reflectoring.coderadar.core.query.domain;

public class RefreshTokenNotFoundException extends UserException {

    public RefreshTokenNotFoundException() {
        super("Refresh token not found. New login is necessary.");
    }
}
