package org.wickedsource.coderadar.security.authentication.rest;

import org.springframework.hateoas.ResourceSupport;

public class UserLoginResource extends ResourceSupport {

    private String username;
    private String password;

    public UserLoginResource(String username, String password) {
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
