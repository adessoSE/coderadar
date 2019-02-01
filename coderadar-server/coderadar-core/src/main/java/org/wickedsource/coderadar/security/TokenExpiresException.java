package org.wickedsource.coderadar.security;

import org.wickedsource.coderadar.core.rest.validation.UserException;

public class TokenExpiresException extends UserException {

	public TokenExpiresException() {
		super("Access token is expired");
	}
}
