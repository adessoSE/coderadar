package org.wickedsource.coderadar.factories.resources;

import org.wickedsource.coderadar.user.domain.UserLoginResource;

public class UserLoginResourceFactory {

  public UserLoginResource userLoginResource() {
    return new UserLoginResource("radar", "Password12!");
  }
}
