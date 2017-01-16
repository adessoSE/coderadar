package org.wickedsource.coderadar.security.domain;

import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;

public class RefreshTokenResource extends ResourceSupport {

    @NotNull
    private String accessToken;

    @NotNull
    private String refreshToken;

    public RefreshTokenResource() {
    }

    public RefreshTokenResource(String accessToken, String refreshToken) {
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
