package org.wickedsource.coderadar.security.domain;

import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;

public class UserRegistrationDataResource extends ResourceSupport {

    @NotNull
    private String username;

    @NotNull
    @Length(min = 8)
    private String password;


    public UserRegistrationDataResource() {
    }

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
