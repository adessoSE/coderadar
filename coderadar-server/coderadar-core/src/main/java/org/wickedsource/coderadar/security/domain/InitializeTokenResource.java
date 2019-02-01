package org.wickedsource.coderadar.security.domain;

import javax.validation.constraints.NotNull;
import org.springframework.hateoas.ResourceSupport;

public class InitializeTokenResource extends ResourceSupport {

	@NotNull private String accessToken;

	@NotNull private String refreshToken;

	public InitializeTokenResource() {}

	public InitializeTokenResource(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
