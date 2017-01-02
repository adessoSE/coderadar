package org.wickedsource.coderadar.security.authentication.rest;

import org.springframework.hateoas.ResourceSupport;

public class AuthenticationTokenResource extends ResourceSupport {

    String token;

    public AuthenticationTokenResource(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
