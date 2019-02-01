package org.wickedsource.coderadar.core.rest.validation;

/** An exception whose message is supposed to be displayed to the user. */
public class UserException extends RuntimeException {

	public UserException(String message) {
		super(message);
	}
}
