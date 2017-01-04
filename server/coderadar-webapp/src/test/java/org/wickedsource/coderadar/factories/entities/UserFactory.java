package org.wickedsource.coderadar.factories.entities;

import org.wickedsource.coderadar.security.domain.User;

public class UserFactory {

    public User registeredUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("john");
        user.setPassword("123456");

        return user;
    }
}
