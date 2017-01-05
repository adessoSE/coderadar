package org.wickedsource.coderadar.security.domain;

import org.springframework.hateoas.ResourceSupport;

public class UserCredentialsResource extends ResourceSupport {

    private String username;
    private String password;


    public UserCredentialsResource() {
    }

    public UserCredentialsResource(String username, String password) {
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
