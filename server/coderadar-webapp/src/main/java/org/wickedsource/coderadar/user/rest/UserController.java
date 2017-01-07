package org.wickedsource.coderadar.user.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.wickedsource.coderadar.core.rest.validation.RegistrationException;
import org.wickedsource.coderadar.user.domain.User;
import org.wickedsource.coderadar.user.domain.UserRegistrationDataResource;
import org.wickedsource.coderadar.user.domain.UserResource;

import javax.validation.Valid;

@Controller
@Transactional
@RequestMapping(path = "/user")
public class UserController {

    private final RegistrationService registrationService;

    private final UserResourceAssembler userResourceAssembler;


    @Autowired
    public UserController(RegistrationService registrationService, UserResourceAssembler userResourceAssembler) {
        this.registrationService = registrationService;
        this.userResourceAssembler = userResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/register")
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
}
