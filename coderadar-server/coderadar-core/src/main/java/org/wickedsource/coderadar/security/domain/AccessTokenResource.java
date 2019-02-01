package org.wickedsource.coderadar.security.domain;

import javax.validation.constraints.NotNull;
import org.springframework.hateoas.ResourceSupport;

public class AccessTokenResource extends ResourceSupport {

	@NotNull private String token;

	public AccessTokenResource() {}

	public AccessTokenResource(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
