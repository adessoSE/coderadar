package org.wickedsource.coderadar.factories.entities;

import org.wickedsource.coderadar.user.domain.User;

public class UserFactory {

  public User registeredUser() {
    User user = new User();
    user.setId(1L);
    user.setUsername("john");
    user.setPassword("12345678");

    return user;
  }
}
