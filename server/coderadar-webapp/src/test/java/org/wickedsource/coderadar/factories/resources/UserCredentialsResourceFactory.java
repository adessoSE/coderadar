package org.wickedsource.coderadar.factories.resources;

import org.wickedsource.coderadar.security.domain.UserCredentialsResource;

public class UserCredentialsResourceFactory {

    public UserCredentialsResource userCredentialsResource() {
        return new UserCredentialsResource("user", "pass");
    }
}
