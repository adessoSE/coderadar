package org.wickedsource.coderadar.user.domain;

import javax.validation.constraints.NotNull;
import org.springframework.hateoas.ResourceSupport;
import org.wickedsource.coderadar.security.ValidPassword;

public class UserRegistrationDataResource extends ResourceSupport {

	@NotNull private String username;

	@ValidPassword private String password;

	public UserRegistrationDataResource() {}

	public UserRegistrationDataResource(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
