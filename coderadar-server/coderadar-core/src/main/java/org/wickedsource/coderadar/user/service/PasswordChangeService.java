package org.wickedsource.coderadar.user.service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.security.domain.ChangePasswordResponseResource;
import org.wickedsource.coderadar.security.domain.RefreshTokenRepository;
import org.wickedsource.coderadar.security.service.PasswordService;
import org.wickedsource.coderadar.user.domain.User;
import org.wickedsource.coderadar.user.domain.UserRepository;
import org.wickedsource.coderadar.user.rest.UserController;

/** Service for change password of a user. */
@Service
public class PasswordChangeService {

  private final PasswordService passwordService;

  private final UserRepository userRepository;

  private final RefreshTokenRepository refreshTokenRepository;

  @Autowired
  public PasswordChangeService(
      PasswordService passwordService,
      UserRepository userRepository,
      RefreshTokenRepository refreshTokenRepository) {
    this.passwordService = passwordService;
    this.userRepository = userRepository;
    this.refreshTokenRepository = refreshTokenRepository;
  }

  public ChangePasswordResponseResource change(String username, String newPassword) {
    String hashedPassword = passwordService.hash(newPassword);
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException(
          String.format("The user with username %s was not found", username));
    }
    user.setPassword(hashedPassword);
    refreshTokenRepository.deleteByUser(user);

    ChangePasswordResponseResource changePasswordResponseResource =
        new ChangePasswordResponseResource();
    changePasswordResponseResource.add(
        linkTo(methodOn(UserController.class).getUser(user.getId())).withRel("self"));
    return changePasswordResponseResource;
  }
}
