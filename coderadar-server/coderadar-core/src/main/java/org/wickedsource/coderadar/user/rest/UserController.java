package org.wickedsource.coderadar.user.rest;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.wickedsource.coderadar.core.rest.validation.AccessTokenNotExpiredException;
import org.wickedsource.coderadar.core.rest.validation.RegistrationException;
import org.wickedsource.coderadar.projectadministration.domain.User;
import org.wickedsource.coderadar.security.domain.*;
import org.wickedsource.coderadar.security.service.TokenService;
import org.wickedsource.coderadar.user.domain.UserLoginResource;
import org.wickedsource.coderadar.user.domain.UserRegistrationDataResource;
import org.wickedsource.coderadar.user.service.LoginService;
import org.wickedsource.coderadar.user.service.PasswordChangeService;
import org.wickedsource.coderadar.user.service.RegistrationService;
import org.wickedsource.coderadar.user.service.TokenRefreshService;

@Controller
@Transactional
@RequestMapping(path = "/user")
public class UserController {

  private final RegistrationService registrationService;

  private final UserResourceAssembler userResourceAssembler;

  private final AuthenticationManager authenticationManager;

  private final LoginService loginService;

  private final TokenService tokenService;

  private final TokenRefreshService tokenRefreshService;

  private final PasswordChangeService passwordChangeService;

  @Autowired
  public UserController(
      RegistrationService registrationService,
      UserResourceAssembler userResourceAssembler,
      AuthenticationManager authenticationManager,
      LoginService loginService,
      TokenService tokenService,
      TokenRefreshService tokenRefreshService,
      PasswordChangeService passwordChangeService) {
    this.registrationService = registrationService;
    this.userResourceAssembler = userResourceAssembler;
    this.authenticationManager = authenticationManager;
    this.loginService = loginService;
    this.tokenService = tokenService;
    this.tokenRefreshService = tokenRefreshService;
    this.passwordChangeService = passwordChangeService;
  }

  @RequestMapping(method = RequestMethod.POST, path = "/registration")
  public ResponseEntity<UserResource> register(
      @Valid @RequestBody UserRegistrationDataResource userRegistrationDataResource) {
    if (registrationService.userExists(userRegistrationDataResource)) {
      throw new RegistrationException(userRegistrationDataResource.getUsername());
    }
    User registeredUser = registrationService.register(userRegistrationDataResource);
    UserResource userResource = userResourceAssembler.toResource(registeredUser);
    return new ResponseEntity<>(userResource, HttpStatus.CREATED);
  }

  @RequestMapping(method = RequestMethod.GET, path = "/{userId}")
  public ResponseEntity<UserResource> getUser(@PathVariable Long userId) {
    User user = registrationService.getUser(userId);
    UserResource userResource = userResourceAssembler.toResource(user);
    return new ResponseEntity<>(userResource, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.POST, path = "/auth")
  public ResponseEntity<InitializeTokenResource> login(
      @Valid @RequestBody UserLoginResource userLoginResource) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                userLoginResource.getUsername(), userLoginResource.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    InitializeTokenResource initializeTokenResource =
        loginService.login(userLoginResource.getUsername());
    return new ResponseEntity<>(initializeTokenResource, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.POST, path = "/refresh")
  public ResponseEntity<AccessTokenResource> refresh(
      @Valid @RequestBody RefreshTokenResource refreshTokenResource) {
    if (tokenService.isExpired(refreshTokenResource.getAccessToken())) {
      String accessToken =
          tokenRefreshService.createAccessToken(refreshTokenResource.getRefreshToken());
      return new ResponseEntity<>(new AccessTokenResource(accessToken), HttpStatus.OK);
    }
    throw new AccessTokenNotExpiredException();
  }

  @RequestMapping(method = RequestMethod.POST, path = "/password/change")
  public ResponseEntity<ChangePasswordResponseResource> changePassword(
      @Valid @RequestBody PasswordChangeResource passwordChangeResource) {
    ChangePasswordResponseResource passwordResponseResource =
        passwordChangeService.change(
            passwordChangeResource.getRefreshToken(), passwordChangeResource.getNewPassword());
    return new ResponseEntity<>(passwordResponseResource, HttpStatus.OK);
  }
}
