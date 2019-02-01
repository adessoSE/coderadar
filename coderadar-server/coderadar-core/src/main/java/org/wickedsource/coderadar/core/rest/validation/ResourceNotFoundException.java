package org.wickedsource.coderadar.core.rest.validation;

public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException() {
		super((String) null);
	}

	public ResourceNotFoundException(String message) {
		super(message);
	}
}
