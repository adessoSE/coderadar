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
import org.wickedsource.coderadar.core.rest.validation.RegistrationException;
import org.wickedsource.coderadar.security.domain.InitializeTokenResource;
import org.wickedsource.coderadar.security.service.TokenService;
import org.wickedsource.coderadar.user.domain.User;
import org.wickedsource.coderadar.user.domain.UserLoginResource;
import org.wickedsource.coderadar.user.domain.UserRegistrationDataResource;
import org.wickedsource.coderadar.user.domain.UserResource;

@Controller
@Transactional
@RequestMapping(path = "/user")
public class UserController {

    private final RegistrationService registrationService;

    private final UserResourceAssembler userResourceAssembler;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    @Autowired
    public UserController(RegistrationService registrationService, UserResourceAssembler userResourceAssembler, AuthenticationManager authenticationManager,
            TokenService tokenService) {
        this.registrationService = registrationService;
        this.userResourceAssembler = userResourceAssembler;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/registration")
    public ResponseEntity<UserResource> register(@Valid @RequestBody UserRegistrationDataResource userRegistrationDataResource) {
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
    public ResponseEntity<InitializeTokenResource> login(@Valid @RequestBody UserLoginResource userLoginResource) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginResource.getUsername(), userLoginResource.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // TODO create and return tokens
        return null;
    }

}
