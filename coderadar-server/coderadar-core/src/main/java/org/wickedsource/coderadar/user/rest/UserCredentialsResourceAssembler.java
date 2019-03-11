package org.wickedsource.coderadar.user.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wickedsource.coderadar.core.rest.AbstractResourceAssembler;
import org.wickedsource.coderadar.security.service.PasswordService;
import org.wickedsource.coderadar.user.domain.User;
import org.wickedsource.coderadar.user.domain.UserRegistrationDataResource;

@Component
public class UserCredentialsResourceAssembler
    extends AbstractResourceAssembler<User, UserRegistrationDataResource> {

  private PasswordService passwordService;

  @Autowired
  public UserCredentialsResourceAssembler(PasswordService passwordService) {
    super(UserController.class, UserRegistrationDataResource.class);
    this.passwordService = passwordService;
  }

  @Override
  public UserRegistrationDataResource toResource(User entity) {
    return null;
  }

  public User toEntity(UserRegistrationDataResource userRegistrationDataResource) {
    User user = new User();
    user.setUsername(userRegistrationDataResource.getUsername());
    user.setPassword(passwordService.hash(userRegistrationDataResource.getPassword()));
    return user;
  }
}
