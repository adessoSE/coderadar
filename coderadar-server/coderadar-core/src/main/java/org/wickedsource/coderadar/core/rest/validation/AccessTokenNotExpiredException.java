package org.wickedsource.coderadar.core.rest.validation;

public class AccessTokenNotExpiredException extends UserException {

	public AccessTokenNotExpiredException() {
		super("Access token ist still valid. This token must be used for authentication.");
	}
}
