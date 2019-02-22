package org.wickedsource.coderadar.user.rest;

import org.springframework.stereotype.Component;
import org.wickedsource.coderadar.core.rest.AbstractResourceAssembler;
import org.wickedsource.coderadar.user.domain.User;
import org.wickedsource.coderadar.user.domain.UserResource;

@Component
public class UserResourceAssembler extends AbstractResourceAssembler<User, UserResource> {

  @Override
  public UserResource toResource(User entity) {
    return new UserResource(entity.getUsername());
  }
}
